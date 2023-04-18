package com.treatwell.testkmm.di

import com.treatwell.testkmm.login.di.loginModule

fun commonsModule() = listOf(
    greetingModule,
    loginModule
)