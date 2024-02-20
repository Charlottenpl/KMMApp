package com.example.kmmapp.entity

import kotlinx.serialization.json.Json



interface CommonCallback {
    fun onSuccess(code: Int, result: Json)

    fun onFail(code: Int, result: Json)

    fun onCancel(code: Int, result: Json)
}


object CallbackBean {
    lateinit var initCallback: CommonCallback
    lateinit var loginCallback: CommonCallback
}