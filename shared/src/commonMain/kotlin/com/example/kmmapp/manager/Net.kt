package com.example.kmmapp.manager

import com.example.kmmapp.entity.NetEntity
import com.example.kmmapp.getRequestParamString
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.cookie
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.http.userAgent
import io.ktor.utils.io.core.toByteArray
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

object Net {
    private val httpClient by lazy { HttpClient() }

    fun sendGet(callback: (String) -> Unit) {
        //完成请求之后，别忘记调用 close() 来关闭和释放 HttpClient 实例，以免造成内存泄露
        GlobalScope.launch(Dispatchers.IO) {
            val res: HttpResponse = httpClient.get("https://www.baidu.com")

            val stringBody: String = res.body()
            callback(stringBody)
        }
    }


    fun sendPost(entity: NetEntity, callback: HttpCallback) {
        var contentType: ContentType = ContentType.Application.Json

        var body: String? = ""
        if (entity.params != null) {
            body = getRequestParamString(entity.params!!)
        } else if (!entity.paramForm.isNullOrBlank()) {
            body = entity.paramForm
        }

        //完成请求之后，别忘记调用 close() 来关闭和释放 HttpClient 实例，以免造成内存泄露
        GlobalScope.launch(Dispatchers.IO) {
            val res: HttpResponse = httpClient.post(entity.url) {
                method = HttpMethod.Post
                setBody(body)
                contentType(contentType)
            }

            val stringBody: String = res.bodyAsText()
            val statusCode = res.status
            if (statusCode.value == 200){
                callback.onSuccess(stringBody)
            }else{
                callback.onFail(statusCode, stringBody)
            }

        }
    }

}


interface HttpCallback{
    fun onSuccess(response: String)

    fun onFail(statusCode: HttpStatusCode, response: String)
}