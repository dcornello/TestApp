package com.treatwell.testkmm.login.data.repository

import com.treatwell.testkmm.login.data.Failure
import com.treatwell.testkmm.login.data.SharedResult
import com.treatwell.testkmm.login.data.Success
import com.treatwell.testkmm.login.data.User
import com.treatwell.testkmm.login.domain.repository.AuthenticationRepository
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth

class AuthenticationRepositoryImpl : AuthenticationRepository {

    override fun isUserLoggedIn(): Boolean {
        return getUserData() != null
    }

    override suspend fun signupUser(email: String, password: String): SharedResult<Throwable, User> {
        return try {
            Firebase.auth.createUserWithEmailAndPassword(email, password).user?.let {
                Success(User(uid = it.uid, email = it.email))
            } ?: Failure(Throwable())
        } catch (e: Exception) {
            Failure(e.cause ?: Throwable())
        }
    }

    override suspend fun loginUser(email: String, password: String): SharedResult<Throwable, User> {
        return try {
            Firebase.auth.signInWithEmailAndPassword(email, password).user?.let {
                Success(User(uid = it.uid, email = it.email))
            } ?: Failure(Throwable())
        } catch (e: Exception) {
            Failure(e.cause ?: Throwable())
        }
    }

    override suspend fun logout(): SharedResult<Throwable, String> {
        return try {
            Firebase.auth.signOut()
            Success("")
        } catch (e: Exception) {
            Failure(e.cause ?: Throwable())
        }
    }

    override fun getUserData(): User? =
        Firebase.auth.currentUser?.let {
            User(uid = it.uid, email = it.email)
        }

}