package com.treatwell.testkmm.login.domain.repository

interface AuthenticationRepository {

    fun isUserLoggedIn(): Boolean

    suspend fun signupUser()

    suspend fun loginUser()

    suspend fun logout()

    fun getUserData()
}