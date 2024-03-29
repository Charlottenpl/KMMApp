package com.example.kmmapp

import android.content.Context
import com.example.kmmapp.entity.CommonCallback

object ZeusSDK {
    lateinit private var context: Context

    fun init(con: Context, appId: String, appKey: String, serviceUrl: String, isDebug: Boolean, callback: CommonCallback?){
        context = con
        SDK.init(appId, appKey, serviceUrl, isDebug, callback)
    }

    fun login(callback: CommonCallback?){
        SDK.login(callback)
    }
}