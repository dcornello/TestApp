package com.treatwell.testkmm.testapp.android.di

import com.treatwell.testkmm.di.commonsModule
import com.treatwell.testkmm.testapp.android.login.di.loginAppModule
import org.koin.core.module.Module

fun applicationModule() = mutableListOf<Module>().apply{
    this.addAll(commonsModule())
    this.add(loginAppModule())
}

