package com.treatwell.testkmm.testapp

class Greeting {
    private val platform: Platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }

    fun getEnumTest(): TEST? {
        return TEST.PAPERINO
    }
}


enum class TEST {
    PIPPO,
    PLUTO,
    PAPERINO
}