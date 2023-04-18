package com.treatwell.testkmm.di

import com.treatwell.testkmm.login.Greeting
import com.treatwell.testkmm.login.domain.usecase.SignUpUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin

class GreetingHelper : KoinComponent {
    private val greet: Greeting by inject()
    fun greet(): String {
        return greet.greet()
    }
}

class LoginHelper : KoinComponent {
    private val signUpUseCase: SignUpUseCase by inject()
}

fun initKoin() {
    startKoin {
        modules(commonsModule())
    }
}