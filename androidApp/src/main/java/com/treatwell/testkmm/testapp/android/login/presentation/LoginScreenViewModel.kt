package com.treatwell.testkmm.testapp.android.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.treatwell.testkmm.login.domain.usecase.EmailValidationThrowable
import com.treatwell.testkmm.login.domain.usecase.EmailValidationUseCase
import com.treatwell.testkmm.login.domain.usecase.PasswordValidationThrowable
import com.treatwell.testkmm.login.domain.usecase.PasswordValidationUseCase
import com.treatwell.testkmm.login.domain.usecase.SignUpUseCase
import com.treatwell.testkmm.login.presentation.EmailError
import com.treatwell.testkmm.login.presentation.LoginScreenSideEffect
import com.treatwell.testkmm.login.presentation.LoginScreenUIState
import com.treatwell.testkmm.login.presentation.LoginScreenViewModelPact
import com.treatwell.testkmm.login.presentation.PasswordError
import com.treatwell.testkmm.login.presentation.SignupError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginScreenViewModel(
    override val signUpUseCase: SignUpUseCase,
    override val emailValidationUseCase: EmailValidationUseCase,
    override val passwordValidationUseCase: PasswordValidationUseCase
) : ViewModel(), LoginScreenViewModelPact {

    override var uiState: LoginScreenUIState
        get() = _viewState.value
        set(newState) {
            _viewState.update { newState }
        }


    private val _viewState = MutableStateFlow(
        LoginScreenUIState(
            email = "cornello.diego89@gmail.com",
            password = "Password123"
        )
    )
    val viewState = _viewState.asStateFlow()

    private val _sideEffects = MutableSharedFlow<LoginScreenSideEffect>()
    val sideEffects: Flow<LoginScreenSideEffect> = _sideEffects.asSharedFlow()

    override fun checkValidityEmail(email: String): Result<Any?> {
        return emailValidationUseCase(email = email)
            .onSuccess {
                uiState = uiState.copy(
                    email = email,
                    emailError = null
                )
            }
            .onFailure { error ->
                when (error) {
                    is EmailValidationThrowable.EmptyEmailThrowable ->
                        uiState = uiState.copy(
                            email = email,
                            emailError = EmailError.Empty
                        )

                    is EmailValidationThrowable.WrongFormatEmailThrowable ->
                        uiState = uiState.copy(
                            email = email,
                            emailError = EmailError.WrongFormat
                        )
                }
            }
    }

    override fun checkValidityPassword(password: String): Result<Any?> {
        return passwordValidationUseCase(password)
            .onSuccess {
                uiState = uiState.copy(
                    password = password,
                    passwordError = null
                )
            }
            .onFailure { error ->
                when (error) {
                    is PasswordValidationThrowable.EmptyPasswordThrowable ->
                        uiState = uiState.copy(
                            password = password,
                            passwordError = PasswordError.Empty
                        )
                    is PasswordValidationThrowable.ShortPasswordThrowable ->
                        uiState = uiState.copy(
                            password = password,
                            passwordError = PasswordError.Short
                        )
                }
            }
    }

    override fun resetErrorState() {
        uiState = uiState.copy(userErrorMessage = null)
    }

    override fun signup() {
        val email = uiState.email
        val password = uiState.password
        val emailCheck = checkValidityEmail(email)
        val passwordCheck = checkValidityPassword(password)
        if (emailCheck.isSuccess && passwordCheck.isSuccess) {
            uiState = uiState.copy(showLoading = true)
            viewModelScope.launch(Dispatchers.IO) {
                signUpUseCase(email = email, password = password)
                    .onFailure {
                        uiState = uiState.copy(
                            showLoading = false,
                            userErrorMessage = SignupError.UNKNOWN
                        )
                    }
                    .onSuccess {
                        uiState = uiState.copy(showLoading = false)
                        sendSideEffect(LoginScreenSideEffect.GoToLogoutScreen)
                    }
            }
        }
    }

    override fun sendSideEffect(sideEffect: LoginScreenSideEffect) {
        viewModelScope.launch { _sideEffects.emit(sideEffect) }
    }
}