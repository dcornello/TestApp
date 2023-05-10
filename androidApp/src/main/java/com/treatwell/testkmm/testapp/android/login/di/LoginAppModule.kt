package com.treatwell.testkmm.testapp.android.login.di

import com.treatwell.testkmm.testapp.android.login.presentation.LoginScreenViewModel
import com.treatwell.testkmm.testapp.android.login.presentation.DashboardScreenViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun loginAppModule() = module {
    singleOf(::LoginScreenViewModel)
    singleOf(::DashboardScreenViewModel)
}