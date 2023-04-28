package com.treatwell.testkmm.login.domain.usecase

import com.treatwell.testkmm.login.data.User
import com.treatwell.testkmm.login.domain.repository.AuthenticationRepository


class SignUpUseCase(private val authenticationRepository: AuthenticationRepository) {

    suspend operator fun invoke(email: String, password: String): Result<User> {
        return authenticationRepository.signupUser(email = email, password = password)
    }
}