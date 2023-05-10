package com.treatwell.testkmm.login.domain.usecase

import com.treatwell.testkmm.login.data.SharedResult
import com.treatwell.testkmm.login.data.User
import com.treatwell.testkmm.login.domain.repository.AuthenticationRepository


class LogInUseCase(private val authenticationRepository: AuthenticationRepository) {

    suspend operator fun invoke(email: String, password: String): SharedResult<Throwable, User> {
        return authenticationRepository.loginUser(email = email, password = password)
    }
}