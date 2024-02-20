package com.example.kmmapp

import SDK_CODE_SUCCESS
import com.example.kmmapp.entity.CallbackBean
import com.example.kmmapp.entity.CommonCallback
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

object SDK {

    fun init(appId: String, appKey: String, serviceUrl: String, isDebug: Boolean, callback: CommonCallback?){
        // 保存callback
        CallbackBean.initCallback = callback
        // 调用init方法
        println("init success")
        if (callback != null) {
            callback.onSuccess(SDK_CODE_SUCCESS, buildJsonObject {
                put("code", SDK_CODE_SUCCESS)
            })
        }
    }

    fun login(callback: CommonCallback?){
        CallbackBean.loginCallback = callback

        if (callback != null) {
            callback.onSuccess(SDK_CODE_SUCCESS, buildJsonObject {
                put("code", SDK_CODE_SUCCESS)
            })
        }
    }
}