package com.treatwell.testkmm.login.domain.usecase

import androidx.core.util.PatternsCompat

class EmailValidationUseCase {
    operator fun invoke(email: String): Result<Any?> {
        return when{
            email.isBlank() -> Result.failure(EmailValidationThrowable.EmptyEmailThrowable)
            PatternsCompat.EMAIL_ADDRESS.matcher(email).matches().not() -> Result.failure(EmailValidationThrowable.WrongFormatEmailThrowable)
            else -> Result.success(null)
        }
    }
}

sealed class EmailValidationThrowable : Throwable() {
    object EmptyEmailThrowable : EmailValidationThrowable()
    object WrongFormatEmailThrowable : EmailValidationThrowable()
}