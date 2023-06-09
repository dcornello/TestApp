package com.treatwell.testkmm.testapp.di

import com.treatwell.testkmm.testapp.Greeting
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin

class GreetingHelper : KoinComponent {
    private val greet: Greeting by inject()
    fun greet(): String {
        return greet.greet()
    }
}

fun initKoin() {
    startKoin {
        modules(commonsModule())
    }
}
