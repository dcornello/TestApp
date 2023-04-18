package com.treatwell.testkmm.testapp.di

import com.treatwell.testkmm.login.di.loginModule

fun commonsModule() = listOf(
    greetingModule,
    loginModule
)