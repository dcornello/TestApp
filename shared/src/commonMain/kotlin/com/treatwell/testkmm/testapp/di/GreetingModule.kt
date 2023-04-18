package com.treatwell.testkmm.testapp.di

import com.treatwell.testkmm.testapp.Greeting
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val greetingModule = module {
    singleOf(::Greeting)
}