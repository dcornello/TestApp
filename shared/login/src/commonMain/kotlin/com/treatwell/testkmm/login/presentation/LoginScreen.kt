package com.treatwell.testkmm.login.presentation

import com.treatwell.testkmm.login.data.SharedResult
import com.treatwell.testkmm.login.domain.usecase.EmailValidationThrowable
import com.treatwell.testkmm.login.domain.usecase.EmailValidationUseCase
import com.treatwell.testkmm.login.domain.usecase.LogInUseCase
import com.treatwell.testkmm.login.domain.usecase.PasswordValidationThrowable
import com.treatwell.testkmm.login.domain.usecase.PasswordValidationUseCase
import com.treatwell.testkmm.login.domain.usecase.SignUpUseCase

interface ILoginScreenViewModel {

    var __uiState: LoginScreenUIState
    val logInUseCase: LogInUseCase
    val signUpUseCase: SignUpUseCase
    val emailValidationUseCase: EmailValidationUseCase
    val passwordValidationUseCase: PasswordValidationUseCase

    val userActions: LoginScreenUserActions
        get() = LoginScreenUserActions(
            onSignupClicked = ::login,
            onEmailValueChanged = ::checkValidityEmail,
            onPasswordValueChanged = ::checkValidityPassword,
            onUserErrorDismissed = ::resetErrorState
        )

    fun checkValidityEmail(email: String): SharedResult<Throwable, Any?> {
        return emailValidationUseCase(email = email).apply {
            fold(
                succeeded = {
                    __uiState = __uiState.copy(
                        email = email,
                        emailError = null
                    )
                },
                failed = { error ->
                    when (error) {
                        is EmailValidationThrowable.EmptyEmailThrowable ->
                            __uiState = __uiState.copy(
                                email = email,
                                emailError = EmailError.Empty
                            )

                        is EmailValidationThrowable.WrongFormatEmailThrowable ->
                            __uiState = __uiState.copy(
                                email = email,
                                emailError = EmailError.WrongFormat
                            )
                    }
                }
            )
        }
    }

    fun checkValidityPassword(password: String): SharedResult<Throwable, Any?> {
        return passwordValidationUseCase(password).apply {
            fold(
                succeeded = {
                    __uiState = __uiState.copy(
                        password = password,
                        passwordError = null
                    )
                },
                failed = { error ->
                    when (error) {
                        is PasswordValidationThrowable.EmptyPasswordThrowable ->
                            __uiState = __uiState.copy(
                                password = password,
                                passwordError = PasswordError.Empty
                            )
                        is PasswordValidationThrowable.ShortPasswordThrowable ->
                            __uiState = __uiState.copy(
                                password = password,
                                passwordError = PasswordError.Short
                            )
                    }
                }
            )
        }
    }

    fun resetErrorState() {
    }

    fun login() {
        val email = __uiState.email
        val password = __uiState.password
        val emailCheck = checkValidityEmail(email)
        val passwordCheck = checkValidityPassword(password)
        if (emailCheck.isSuccess() && passwordCheck.isSuccess()) {
            __uiState = __uiState.copy(showLoading = true)
            launchInViewModelScope {
                logInUseCase(email = email, password = password)
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

    fun sendSideEffect(sideEffect: LoginScreenSideEffect)

    fun launchInViewModelScope(function: suspend () -> Unit)
}

// Use this for iOs automatic implementation
abstract class LoginScreenViewModel : ILoginScreenViewModel

data class LoginScreenUIState(
    val email: String = "",
    val password: String = "",
    val emailError: EmailError? = null,
    val passwordError: PasswordError? = null,
    val showLoading: Boolean = false
)

enum class EmailError {
    Empty,
    WrongFormat
}

enum class PasswordError {
    Empty,
    Short
}

data class LoginScreenUserActions(
    val onSignupClicked: () -> Unit,
    val onEmailValueChanged: (email: String) -> Unit,
    val onPasswordValueChanged: (password: String) -> Unit,
    val onUserErrorDismissed: () -> Unit
)

sealed class LoginScreenSideEffect {
    object GoToLogoutScreen : LoginScreenSideEffect()
    object ShowLogInError : LoginScreenSideEffect()
}