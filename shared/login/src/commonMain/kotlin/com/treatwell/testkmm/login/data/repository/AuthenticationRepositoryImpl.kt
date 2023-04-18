package com.treatwell.testkmm.login.data.repository

import com.treatwell.testkmm.login.domain.repository.AuthenticationRepository
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth

class AuthenticationRepositoryImpl : AuthenticationRepository {

    override fun isUserLoggedIn(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun signupUser() {
        Firebase.auth.createUserWithEmailAndPassword("diego.cornello@treatwell.com", "Password_123")
    }

    override suspend fun loginUser() {
        TODO("Not yet implemented")
    }

    override suspend fun logout() {
        TODO("Not yet implemented")
    }

    override fun getUserData() {
        TODO("Not yet implemented")
    }
}