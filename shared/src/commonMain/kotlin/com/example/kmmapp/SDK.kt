package com.example.kmmapp

import SDK_CODE_SUCCESS
import com.example.kmmapp.entity.CallbackBean
import com.example.kmmapp.entity.CommonCallback
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.ProxyBuilder
import io.ktor.client.engine.http
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.cookie
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpMethod
import io.ktor.http.userAgent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

object SDK {

    val httpClient by lazy { HttpClient {
        engine {
            proxy = ProxyBuilder.http("http://127.0.0.1:8888")
            threadsCount = 4
        }
        defaultRequest {
            // 可配置公共的 Cookies、Headers、Params
        }
    } }

    fun sendGet(callback: (String)->Unit) {
        //完成请求之后，别忘记调用 close() 来关闭和释放 HttpClient 实例，以免造成内存泄露
        GlobalScope.launch(Dispatchers.Default) {
            val res: HttpResponse = httpClient.get("https://www.baidu.com"){
                method = HttpMethod.Get
                header("TestHeader", "1")
                header("MyHeader", "2")
                userAgent("KMM Http Client")
                cookie("USER_ID", "123456")

                formData {
                    // 示例写法，实际需要处理字节流
                    append("image", ByteArray(256))
                }
            }

            val stringBody: String = res.body()
            callback(stringBody)
        }
    }


    fun sendPost(callback: (String)->Unit) {
        //完成请求之后，别忘记调用 close() 来关闭和释放 HttpClient 实例，以免造成内存泄露
        GlobalScope.launch(Dispatchers.Default) {
            val res: HttpResponse = httpClient.post("https://www.baidu.com"){
                method = HttpMethod.Post
                header("TestHeader", "1")
                header("MyHeader", "2")
                userAgent("KMM Http Client")
                cookie("USER_ID", "123456")

                formData {
                    // 示例写法，实际需要处理字节流
                    append("image", ByteArray(256))
                }
            }

            val stringBody: String = res.bodyAsText()
            callback(stringBody)
        }
    }


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