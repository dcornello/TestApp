package com.treatwell.testkmm.login.domain.usecase

import com.treatwell.testkmm.login.domain.repository.AuthenticationRepository


class SignUpUseCase(private val authenticationRepository: AuthenticationRepository) {

    suspend operator fun invoke() {
        authenticationRepository.signupUser()
    }
}