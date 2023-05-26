package com.treatwell.testkmm.login.domain.repository

import com.treatwell.testkmm.login.data.SharedResult
import com.treatwell.testkmm.login.data.User

interface AuthenticationRepository {

    fun isUserLoggedIn(): Boolean

    suspend fun signupUser(email: String = "diego.cornello@treatwell.com", password: String = "Password123"): SharedResult<Throwable, User>

    suspend fun loginUser(email: String = "diego.cornello@treatwell.com", password: String = "Password123"): SharedResult<Throwable, User>

    suspend fun logout(): SharedResult<Throwable, String>

    fun getUserData(): User?
}