package com.treatwell.testkmm.login.domain.repository

interface AuthenticationRepository {

    fun isUserLoggedIn(): Boolean

    fun signupUser()

    fun loginUser()

    fun logout()

    fun getUserData()
}