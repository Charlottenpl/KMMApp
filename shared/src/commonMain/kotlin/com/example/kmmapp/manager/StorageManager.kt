package com.example.kmmapp.manager

object StorageManager {

    open fun read(key:String): Any?{
        return StorageUtil.read(key)
    }

    open fun write(key: String, value: Any?): Boolean{
        return StorageUtil.write(key, value)
    }
}


expect object StorageUtil {

    open fun read(key:String): Any?

    open fun write(key: String, value: Any?): Boolean
}