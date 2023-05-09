package com.treatwell.testkmm.login.data

sealed class SharedResult<out F, out S> {

    inline fun <T> fold(failed: (F) -> T, succeeded: (S) -> T): T =
        when (this) {
            is Failure -> failed(failure)
            is Success -> succeeded(output)
        }
}

data class Failure<out F>(val failure: F) : SharedResult<F, Nothing>()

data class Success<out S>(val output: S) : SharedResult<Nothing, S>()