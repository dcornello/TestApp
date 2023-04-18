package com.treatwell.testkmm.testapp.di

import com.treatwell.testkmm.testapp.Greeting
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GreetingHelper : KoinComponent {
    private val greet: Greeting by inject()
    fun greet(): String {
        return greet.greet()
    }
}
