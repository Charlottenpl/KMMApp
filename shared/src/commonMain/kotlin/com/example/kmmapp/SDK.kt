package com.example.kmmapp

import com.example.kmmapp.entity.CallbackBean
import com.example.kmmapp.entity.CommonCallback
import com.example.kmmapp.manager.StorageManager

object SDK {

    fun init(appId: String, appKey: String, serviceUrl: String, isDebug: Boolean, callback: CommonCallback){
        // 保存callback
        CallbackBean.initCallback = callback
        // 调用init方法
    }

    fun login(callback: CommonCallback){
        CallbackBean.loginCallback = callback
    }
}