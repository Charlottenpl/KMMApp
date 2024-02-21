package com.example.kmmapp

import com.example.kmmapp.Common.SDK_APP_KEY
import com.example.kmmapp.Common.SDK_VERSION
import com.example.kmmapp.util.NetUtil.encode
import com.example.kmmapp.util.NetUtil.stringToMD5
import org.json.JSONException
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.Collections

class AndroidPlatform : Platform {
    override val name: String = "Android ${android.os.Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual fun getRequestParamString(paramMap: HashMap<String, String>): String? {
    if (paramMap.isEmpty()) {
        return ""
    }
    paramMap.put("sdk_version", SDK_VERSION) //SDK版本
    paramMap.put("platform", "android")

    val mappingList: List<MutableMap.MutableEntry<String, String>> = paramMap.entries.toList()
    Collections.sort(
        mappingList
    ) { (key), (key1) -> key.compareTo(key1) }
    val jsonparam = JSONObject()
    var values = ""
    try {
        for ((key1, value) in mappingList) {
            val key = key1.trim { it <= ' ' }
            jsonparam.put(key, value)
            values += key
            values += value
        }

        val md5key: String? =
            stringToMD5(values.trim { it <= ' ' } + SDK_APP_KEY)
        jsonparam.put("sign", md5key)
        println("sign$md5key")
        print("SDKtest:$jsonparam")
        return encode(jsonparam.toString().toByteArray())
    } catch (e: JSONException) {
        e.printStackTrace()
    }
    return ""
}

actual fun getDeviceID(): String {
    return "123523465563"
}