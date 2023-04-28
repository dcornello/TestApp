package com.treatwell.testkmm.login.domain.repository

import com.treatwell.testkmm.login.data.User

interface AuthenticationRepository {

    fun isUserLoggedIn(): Boolean

    suspend fun signupUser(email: String = "diego.cornello@treatwell.com", password: String = "Password_123"): Result<User>

    suspend fun loginUser()

    suspend fun logout()

    fun getUserData()
}