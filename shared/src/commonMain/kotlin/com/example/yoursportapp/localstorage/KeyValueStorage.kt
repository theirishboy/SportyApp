package com.example.yoursportapp.localstorage

interface KeyValueStorage {
    // #1 - Primitive type
    var token: String?


    fun cleanStorage()
}
