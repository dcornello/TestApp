package com.treatwell.testkmm.testapp.android.login.di

import com.treatwell.testkmm.testapp.android.login.presentation.LoginScreenViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun loginAppModule() = module {
    singleOf(::LoginScreenViewModel)
}