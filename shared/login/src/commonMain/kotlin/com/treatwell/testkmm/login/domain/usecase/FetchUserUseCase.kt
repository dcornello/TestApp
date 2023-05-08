package com.treatwell.testkmm.login.domain.usecase

import com.treatwell.testkmm.login.data.User
import com.treatwell.testkmm.login.domain.repository.AuthenticationRepository

class FetchUserUseCase(private val authenticationRepository: AuthenticationRepository) {
    operator fun invoke(): User? {
        return authenticationRepository.getUserData()
    }
}