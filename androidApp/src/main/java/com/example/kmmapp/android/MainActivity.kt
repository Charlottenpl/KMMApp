package com.example.kmmapp.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.kmmapp.SDK
import com.example.kmmapp.ZeusSDK
import com.example.kmmapp.entity.CommonCallback
import kotlinx.serialization.json.JsonObject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GreetingView(text = "echo", loginUnit = {
                        SDK.login(object : CommonCallback{
                            override fun onSuccess(code: Int, result: JsonObject) {
                                // 实现 onSuccess 方法的具体逻辑
                                println("Success: $code, $result")
                            }

                            override fun onFail(code: Int, result: JsonObject) {
                                // 实现 onFail 方法的具体逻辑
                                println("Fail: $code, $result")
                            }

                            override fun onCancel(code: Int, result: JsonObject) {
                                // 实现 onCancel 方法的具体逻辑
                                println("Cancel: $code, $result")
                            }

                        })
                    }, initUnit = {
                        ZeusSDK.init(
                            this,
                            "7AVS2D5QH2TV",
                            "DF864TCE1XWZE1NH",
                            "https://abroad.topjoy.com/",
                            true,
                            object : CommonCallback {
                                override fun onSuccess(code: Int, result: JsonObject) {
                                    // 实现 onSuccess 方法的具体逻辑
                                    println("Success: $code, $result")
                                }

                                override fun onFail(code: Int, result: JsonObject) {
                                    // 实现 onFail 方法的具体逻辑
                                    println("Fail: $code, $result")
                                }

                                override fun onCancel(code: Int, result: JsonObject) {
                                    // 实现 onCancel 方法的具体逻辑
                                    println("Cancel: $code, $result")
                                }
                            })
                    })
                }
            }
        }
    }
}

@Composable
fun GreetingView(text: String, loginUnit: () -> Unit, initUnit: () -> Unit) {
    Column {
        Text(text = text)
        Button(onClick = initUnit) {
            Text(text = "init")
        }

        Button(onClick = loginUnit) {
            Text(text = "login")
        }
    }

}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        GreetingView("Hello, Android!", {}, {})
    }
}
