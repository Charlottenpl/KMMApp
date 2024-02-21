package com.example.kmmapp

import SDK_CODE_SUCCESS
import com.example.kmmapp.Common.SDK_ACCOUNT
import com.example.kmmapp.Common.SDK_APP_ID
import com.example.kmmapp.Common.SDK_APP_KEY
import com.example.kmmapp.Common.SDK_USER_ID
import com.example.kmmapp.Common.UrlConfig
import com.example.kmmapp.entity.CallbackBean
import com.example.kmmapp.entity.CommonCallback
import com.example.kmmapp.entity.NetEntity
import com.example.kmmapp.manager.HttpCallback
import com.example.kmmapp.manager.Net.sendPost
import io.ktor.http.HttpStatusCode
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

object SDK {


    fun init(appId: String, appKey: String, serviceUrl: String, isDebug: Boolean, callback: CommonCallback?){
        // 保存callback
        CallbackBean.initCallback = callback

        SDK_APP_ID = appId
        SDK_APP_KEY = appKey

        // 调用init方法
        val entity = NetEntity(UrlConfig.gameInfo)

        entity.add("appid", appId)
            .add("lang", "1")

        sendPost(entity, object : HttpCallback{
            override fun onSuccess(response: String) {
                callback?.onSuccess(SDK_CODE_SUCCESS, buildJsonObject {
                    put("code", SDK_CODE_SUCCESS)
                    put("result", response)
                })
            }

            override fun onFail(statusCode: HttpStatusCode, response: String) {
                TODO("Not yet implemented")
            }

        })
    }

    fun login(callback: CommonCallback?){
        CallbackBean.loginCallback = callback


        if (SDK_USER_ID.isNotEmpty()){
            // 调用init方法
            val entity = NetEntity(UrlConfig.login)

            entity.add("appid", SDK_APP_ID)
                .add("account", SDK_ACCOUNT)
                .add("lang", "1")
                .add("device", getDeviceID())

            sendPost(entity, object : HttpCallback{
                override fun onSuccess(response: String) {
                    println(response)
                    callback?.onSuccess(SDK_CODE_SUCCESS, buildJsonObject {
                        put("code", SDK_CODE_SUCCESS)
                    })
                }

                override fun onFail(statusCode: HttpStatusCode, response: String) {
                    TODO("Not yet implemented")
                }

            })

        }else {

        }


    }
}