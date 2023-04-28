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
    private val signUpUseCase: SignUpUseCase,
    private val emailValidationUseCase: EmailValidationUseCase,
    private val passwordValidationUseCase: PasswordValidationUseCase
) : ViewModel(), LoginScreenViewModelPact {

    override val uiState: LoginScreenUIState
        get() = LoginScreenUIState()

    private val _viewState = MutableStateFlow(uiState)
    val viewState = _viewState.asStateFlow()

    private val _sideEffects = MutableSharedFlow<LoginScreenSideEffect>()
    val sideEffects: Flow<LoginScreenSideEffect> = _sideEffects.asSharedFlow()

    override fun checkValidityEmail(email: String): Result<Any?> {
        return emailValidationUseCase(email = email)
            .onSuccess {
                _viewState.update {
                    it.copy(
                        email = email,
                        emailError = null
                    )
                }
            }
            .onFailure { error ->
                when (error) {
                    is EmailValidationThrowable.EmptyEmailThrowable -> _viewState.update {
                        it.copy(
                            email = email,
                            emailError = EmailError.Empty
                        )
                    }
                    is EmailValidationThrowable.WrongFormatEmailThrowable -> _viewState.update {
                        it.copy(
                            email = email,
                            emailError = EmailError.WrongFormat
                        )
                    }
                }
            }
    }

    override fun checkValidityPassword(password: String): Result<Any?> {
        return passwordValidationUseCase(password)
            .onSuccess {
                _viewState.update {
                    it.copy(
                        password = password,
                        passwordError = null
                    )
                }
            }
            .onFailure { error ->
                when (error) {
                    is PasswordValidationThrowable.EmptyPasswordThrowable -> _viewState.update {
                        it.copy(
                            password = password,
                            passwordError = PasswordError.Empty
                        )
                    }
                    is PasswordValidationThrowable.ShortPasswordThrowable -> _viewState.update {
                        it.copy(
                            password = password,
                            passwordError = PasswordError.Short
                        )
                    }
                }
            }
    }

    override fun resetErrorState() {
        _viewState.update { it.copy(userErrorMessage = null) }
    }

    override fun signup() {
        val emailCheck = checkValidityEmail(viewState.value.email)
        val passwordCheck = checkValidityPassword(viewState.value.password)
        if (emailCheck.isSuccess && passwordCheck.isSuccess) {
            _viewState.update { it.copy(showLoading = true) }
            viewModelScope.launch(Dispatchers.IO) {
                signUpUseCase()
                    .onFailure {
                        _viewState.update {
                            it.copy(
                                showLoading = false,
                                userErrorMessage = SignupError.UNKNOWN
                            )
                        }
                    }
                    .onSuccess {
                        _viewState.update { it.copy(showLoading = false) }
                        sendSideEffect(LoginScreenSideEffect.GoToLogoutScreen)
                    }
            }
        }
    }

    override fun sendSideEffect(sideEffect: LoginScreenSideEffect) {
        viewModelScope.launch { _sideEffects.emit(sideEffect) }
    }
}