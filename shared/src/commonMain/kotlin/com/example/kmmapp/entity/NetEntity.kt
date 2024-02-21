package com.example.kmmapp.entity

data class NetEntity(var url: String,                           //请求地址
                     var header: Map<String, String>? = null,   //请求头参数
                     var params: HashMap<String, String>? = null,   //请求参数
                     var paramForm: String? = null){            //网络请求描述

    open fun add(key: String, value: String):NetEntity{
        if (params == null) {
            params = HashMap<String, String>()
        }

        params!!.put(key, value)
        return this
    }
}