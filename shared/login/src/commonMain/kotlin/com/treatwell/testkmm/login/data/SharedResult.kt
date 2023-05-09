package com.treatwell.testkmm.login.data

sealed class SharedResult<out T : Any> {
    data class Success<out T : Any>(val output: T) : SharedResult<T>()
    data class Error(val throwable: Throwable) : SharedResult<Nothing>()
}