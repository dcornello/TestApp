package com.treatwell.testkmm.login.domain.usecase

import com.treatwell.testkmm.login.domain.repository.AuthenticationRepository


class IsUserLoggedInUseCase(private val authenticationRepository: AuthenticationRepository) {

    operator fun invoke(): Boolean {
        return false//authenticationRepository.isUserLoggedIn()
    }
}