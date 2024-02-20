package com.example.kmmapp.manager

class StorageManager {

    open fun read(key:String): Any?{

    }

    open fun write(key: String, value: Any?){

    }
}


expect class StorageUtil {

    open fun read(key:String): Any?

    open fun write(key: String, value: Any?)
}