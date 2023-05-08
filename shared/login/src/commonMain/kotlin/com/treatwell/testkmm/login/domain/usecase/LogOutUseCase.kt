package com.treatwell.testkmm.login.domain.usecase

import com.treatwell.testkmm.login.domain.repository.AuthenticationRepository

class LogOutUseCase(private val authenticationRepository: AuthenticationRepository) {
    suspend operator fun invoke(): Result<String> {
        return authenticationRepository.logout()
    }
}