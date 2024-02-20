package com.example.kmmapp

import com.example.kmmapp.entity.CommonCallback

object ZeusSDK {

    fun init(appId: String, appKey: String, serviceUrl: String, isDebug: Boolean, callback: CommonCallback?){
        SDK.init(appId, appKey, serviceUrl, isDebug, callback)
    }

    fun login(callback: CommonCallback?){
        SDK.login(callback)
    }
}