package com.treatwell.testkmm.login.domain.usecase

import com.treatwell.testkmm.login.data.Failure
import com.treatwell.testkmm.login.data.SharedResult
import com.treatwell.testkmm.login.data.Success

class EmailValidationUseCase{
    operator fun invoke(email: String): SharedResult<Throwable, Any?> {
        return when {
            email.isBlank() -> Failure(EmailValidationThrowable.EmptyEmailThrowable)
            email.matches(emailAddressRegex).not() -> Failure(EmailValidationThrowable.WrongFormatEmailThrowable)
            else -> Success(null)
        }
    }

    private val emailAddressRegex = Regex(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )
}

sealed class EmailValidationThrowable : Throwable() {
    object EmptyEmailThrowable : EmailValidationThrowable()
    object WrongFormatEmailThrowable : EmailValidationThrowable()
}