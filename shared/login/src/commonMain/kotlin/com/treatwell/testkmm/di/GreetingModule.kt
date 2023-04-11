package com.treatwell.testkmm.di

import com.treatwell.testkmm.login.Greeting
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val greetingModule = module {
    singleOf(::Greeting)
}