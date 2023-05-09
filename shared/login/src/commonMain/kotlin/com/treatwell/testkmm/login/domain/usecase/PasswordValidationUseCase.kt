package com.treatwell.testkmm.login.domain.usecase

import com.treatwell.testkmm.login.data.Failure
import com.treatwell.testkmm.login.data.SharedResult
import com.treatwell.testkmm.login.data.Success

class PasswordValidationUseCase {
    operator fun invoke(password: String): SharedResult<Throwable, Any?> {
        return when {
            password.isBlank() -> Failure(PasswordValidationThrowable.EmptyPasswordThrowable)
            password.length <= MIN_PASSWORD_LENGTH -> Failure(PasswordValidationThrowable.ShortPasswordThrowable)
            else -> Success(null)
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