package com.treatwell.testkmm.testapp.android.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.treatwell.testkmm.login.domain.usecase.EmailValidationUseCase
import com.treatwell.testkmm.login.domain.usecase.LogInUseCase
import com.treatwell.testkmm.login.domain.usecase.PasswordValidationUseCase
import com.treatwell.testkmm.login.domain.usecase.SignUpUseCase
import com.treatwell.testkmm.login.presentation.ILoginScreenViewModel
import com.treatwell.testkmm.login.presentation.LoginScreenSideEffect
import com.treatwell.testkmm.login.presentation.LoginScreenUIState
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
    override val passwordValidationUseCase: PasswordValidationUseCase,
    override val logInUseCase: LogInUseCase
) : ViewModel(), ILoginScreenViewModel {

    override var __uiState: LoginScreenUIState
        get() = _viewState.value
        set(newState) {
            _viewState.update { newState }
        }


    private val _viewState = MutableStateFlow(
        LoginScreenUIState()
    )
    val viewState = _viewState.asStateFlow()

    private val _sideEffects = MutableSharedFlow<LoginScreenSideEffect>()
    val sideEffects: Flow<LoginScreenSideEffect> = _sideEffects.asSharedFlow()

//    override fun checkValidityEmail(email: String): SharedResult<Throwable, Any?> {
//        return emailValidationUseCase(email = email).apply {
//            fold(
//                succeeded = {
//                    __uiState = __uiState.copy(
//                        email = email,
//                        emailError = null
//                    )
//                },
//                failed = { error ->
//                    when (error) {
//                        is EmailValidationThrowable.EmptyEmailThrowable ->
//                            __uiState = __uiState.copy(
//                                email = email,
//                                emailError = EmailError.Empty
//                            )
//
//                        is EmailValidationThrowable.WrongFormatEmailThrowable ->
//                            __uiState = __uiState.copy(
//                                email = email,
//                                emailError = EmailError.WrongFormat
//                            )
//                    }
//                }
//            )
//        }
//    }

//    override fun checkValidityPassword(password: String): SharedResult<Throwable, Any?> {
//        return passwordValidationUseCase(password).apply {
//            fold(
//                succeeded = {
//                    __uiState = __uiState.copy(
//                        password = password,
//                        passwordError = null
//                    )
//                },
//                failed = { error ->
//                    when (error) {
//                        is PasswordValidationThrowable.EmptyPasswordThrowable ->
//                            __uiState = __uiState.copy(
//                                password = password,
//                                passwordError = PasswordError.Empty
//                            )
//                        is PasswordValidationThrowable.ShortPasswordThrowable ->
//                            __uiState = __uiState.copy(
//                                password = password,
//                                passwordError = PasswordError.Short
//                            )
//                    }
//                }
//            )
//        }
//    }

//    override fun resetErrorState() {
//        __uiState = __uiState.copy(userErrorMessage = null)
//    }

    override fun login() {
        val email = __uiState.email
        val password = __uiState.password
        val emailCheck = checkValidityEmail(email)
        val passwordCheck = checkValidityPassword(password)
        if (emailCheck.isSuccess() && passwordCheck.isSuccess()) {
            __uiState = __uiState.copy(showLoading = true)
            viewModelScope.launch(Dispatchers.IO) {
                signUpUseCase(email = email, password = password)
                    .fold(
                        failed = {
                            __uiState = __uiState.copy(
                                showLoading = false
                            )
                            sendSideEffect(LoginScreenSideEffect.ShowLogInError)
                        },
                        succeeded = {
                            __uiState = __uiState.copy(showLoading = false)
                            sendSideEffect(LoginScreenSideEffect.GoToLogoutScreen)
                        }
                    )
            }
        }
    }

    override fun sendSideEffect(sideEffect: LoginScreenSideEffect) {
        viewModelScope.launch { _sideEffects.emit(sideEffect) }
    }
}