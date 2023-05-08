package com.treatwell.testkmm.login.data.repository

import com.treatwell.testkmm.login.data.User
import com.treatwell.testkmm.login.domain.repository.AuthenticationRepository
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth

class AuthenticationRepositoryImpl : AuthenticationRepository {

    override fun isUserLoggedIn(): Boolean {
        return getUserData() != null
    }

    override suspend fun signupUser(email: String, password: String): Result<User> {
        return try {
            Firebase.auth.createUserWithEmailAndPassword(email, password).user?.let {
                Result.success(User(uid = it.uid, email = it.email))
            } ?: Result.failure(Throwable())
        } catch (e: Exception) {
            Result.failure(e.cause ?: Throwable())
        }
    }

    override suspend fun loginUser(email: String, password: String): Result<User> {
        return try {
            Firebase.auth.signInWithEmailAndPassword(email, password).user?.let {
                Result.success(User(uid = it.uid, email = it.email))
            } ?: Result.failure(Throwable())
        } catch (e: Exception) {
            Result.failure(e.cause ?: Throwable())
        }
    }

    override suspend fun logout(): Result<String> {
        return try {
            Firebase.auth.signOut()
            Result.success("")
        } catch (e: Exception) {
            Result.failure(e.cause ?: Throwable())
        }
    }

    override fun getUserData(): User? =
        Firebase.auth.currentUser?.let {
            User(uid = it.uid, email = it.email)
        }

}