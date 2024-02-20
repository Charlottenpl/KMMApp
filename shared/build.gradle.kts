plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    kotlin("plugin.serialization").version("1.9.22")
    id("app.cash.sqldelight").version("2.0.0")
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }


    sourceSets {
        commonMain.dependencies {
            //put your multiplatform dependencies here
            implementation(libs.kotlinx.coroutines.core)  //在libs.versions.toml 文件中定义
            implementation(libs.ktor.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json) //序列化模块
            implementation(libs.runtime)
            implementation(libs.kotlinx.datetime)
        }

        androidMain.dependencies {
            implementation(libs.ktor.client.android)
            implementation(libs.android.driver)
            api(libs.ktor.serialization.kotlinx.json) //序列化模块
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
            implementation(libs.native.driver)
            implementation(libs.ktor.serialization.kotlinx.json) //序列化模块
        }


        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "com.example.kmmapp"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
}


// SQLDelight 库允许您从 SQL 查询生成类型安全的 Kotlin 数据库 API
sqldelight {
    databases{
        create("AppDB"){
            packageName.set("com.dao")
        }
    }
}