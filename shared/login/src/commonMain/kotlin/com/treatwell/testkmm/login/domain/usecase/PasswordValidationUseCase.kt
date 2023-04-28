package com.treatwell.testkmm.login.domain.usecase

class PasswordValidationUseCase {
    operator fun invoke(password: String): Result<Any?> {
        return when {
            password.isBlank() -> Result.failure(PasswordValidationThrowable.EmptyPasswordThrowable)
            password.length >= MIN_PASSWORD_LENGTH -> Result.failure(PasswordValidationThrowable.ShortPasswordThrowable)
            else -> Result.success(null)
        }
    }

    companion object {
        private const val MIN_PASSWORD_LENGTH = 8
    }
}

sealed class PasswordValidationThrowable : Throwable() {
    object EmptyPasswordThrowable : PasswordValidationThrowable()
    object ShortPasswordThrowable : PasswordValidationThrowable()
}