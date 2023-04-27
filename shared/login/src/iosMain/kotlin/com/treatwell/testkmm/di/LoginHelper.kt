package com.treatwell.testkmm.di

import com.treatwell.testkmm.login.domain.usecase.SignUpUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin

class LoginHelper : KoinComponent {
    val signUpUseCase: SignUpUseCase by inject()
}