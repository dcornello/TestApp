package com.treatwell.testkmm.login

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform