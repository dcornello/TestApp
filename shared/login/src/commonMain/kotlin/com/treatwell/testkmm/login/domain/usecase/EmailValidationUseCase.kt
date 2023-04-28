package com.treatwell.testkmm.login.domain.usecase

class EmailValidationUseCase{
    operator fun invoke(email: String): Result<Any?> {
        return when {
            email.isBlank() -> Result.failure(EmailValidationThrowable.EmptyEmailThrowable)
            email.matches(emailAddressRegex).not() -> Result.failure(EmailValidationThrowable.WrongFormatEmailThrowable)
            else -> Result.success(null)
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