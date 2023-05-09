package com.treatwell.testkmm.login.domain.usecase

import com.treatwell.testkmm.login.data.SharedResult
import com.treatwell.testkmm.login.domain.repository.AuthenticationRepository

class LogOutUseCase(private val authenticationRepository: AuthenticationRepository) {
    suspend operator fun invoke(): SharedResult<Throwable, String> {
        return authenticationRepository.logout()
    }
}