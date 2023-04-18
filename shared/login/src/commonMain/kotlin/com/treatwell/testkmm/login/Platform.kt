package com.treatwell.testkmm.login

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.app
import dev.gitlive.firebase.initialize

interface Platform {
    val name: String

    fun initialize(context: Any?) {
        Firebase.initialize(context)
        Firebase.app.name
    }
}

expect fun getPlatform(): Platform