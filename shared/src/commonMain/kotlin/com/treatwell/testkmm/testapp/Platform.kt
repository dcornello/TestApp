package com.treatwell.testkmm.testapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform