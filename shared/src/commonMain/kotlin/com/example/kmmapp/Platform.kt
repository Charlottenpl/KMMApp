package com.example.kmmapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect fun getRequestParamString(paramMap: HashMap<String, String>): String?

expect fun getDeviceID(): String