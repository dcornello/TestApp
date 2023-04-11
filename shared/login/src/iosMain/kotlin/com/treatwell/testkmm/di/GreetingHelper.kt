package com.treatwell.testkmm.di

import org.koin.core.component.KoinComponent

class GreetingHelper : KoinComponent {
    private val greet: Greeting by inject()
    fun greet(): String {
        return greet.greeting()
    }
}

fun initKoin() {
    startKoin {
        modules(appModule())
    }
}