package com.treatwell.testkmm.di

import com.treatwell.testkmm.login.domain.usecase.IsUserLoggedInUseCase
import com.treatwell.testkmm.login.domain.usecase.LogInUseCase
import com.treatwell.testkmm.login.domain.usecase.SignUpUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class LoginHelper : KoinComponent {
    val signUpUseCase: SignUpUseCase by inject()
    val logInUseCase: LogInUseCase by inject()
    val isUserLoggedInUseCase: IsUserLoggedInUseCase by inject()
}