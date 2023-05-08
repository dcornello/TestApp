package com.treatwell.testkmm.login.presentation

import com.treatwell.testkmm.login.domain.usecase.EmailValidationUseCase
import com.treatwell.testkmm.login.domain.usecase.PasswordValidationUseCase
import com.treatwell.testkmm.login.domain.usecase.SignUpUseCase

interface LoginScreenViewModelPact {

    var uiState: LoginScreenUIState
    val signUpUseCase: SignUpUseCase
    val emailValidationUseCase: EmailValidationUseCase
    val passwordValidationUseCase: PasswordValidationUseCase

    val userActions: LoginScreenUserActions
        get() = LoginScreenUserActions(
            onSignupClicked = ::signup,
            onEmailValueChanged = ::checkValidityEmail,
            onPasswordValueChanged = ::checkValidityPassword,
            onUserErrorDismissed = ::resetErrorState
        )

    fun checkValidityEmail(email: String): Result<Any?>

    fun checkValidityPassword(password: String): Result<Any?>

    fun resetErrorState()

    fun signup()

    fun sendSideEffect(sideEffect: LoginScreenSideEffect)
}

data class LoginScreenUIState(
    val email: String = "",
    val password: String = "",
    val emailError: EmailError? = null,
    val passwordError: PasswordError? = null,
    val showLoading: Boolean = false,
    val userErrorMessage: SignupError? = null
)

enum class EmailError {
    Empty,
    WrongFormat
}

enum class PasswordError {
    Empty,
    Short
}

enum class SignupError {
    UNKNOWN
}

data class LoginScreenUserActions(
    val onSignupClicked: () -> Unit,
    val onEmailValueChanged: (email: String) -> Unit,
    val onPasswordValueChanged: (password: String) -> Unit,
    val onUserErrorDismissed: () -> Unit
)

sealed class LoginScreenSideEffect {
    object GoToLogoutScreen : LoginScreenSideEffect()
}