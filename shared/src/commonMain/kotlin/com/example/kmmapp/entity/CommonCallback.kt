package com.example.kmmapp.entity

import kotlinx.serialization.json.JsonObject


interface CommonCallback {
    fun onSuccess(code: Int, result: JsonObject)

    fun onFail(code: Int, result: JsonObject)

    fun onCancel(code: Int, result: JsonObject)
}


object CallbackBean {
    var initCallback: CommonCallback? = null
    var loginCallback: CommonCallback? = null
}