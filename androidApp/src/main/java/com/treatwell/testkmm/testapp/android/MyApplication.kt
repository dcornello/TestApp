package com.treatwell.testkmm.testapp.android

import android.app.Application
import com.treatwell.testkmm.login.getPlatform
import com.treatwell.testkmm.testapp.android.di.applicationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        getPlatform().initialize(this)
        startKoin {
            androidContext(this@MyApplication)
            modules(
                applicationModule()
            )
        }
    }
}