package com.example.yoursportapp.localstorage

enum class StorageKeys {
    TOKEN,
    LOGIN_INFO;

    val key get() = this.name
}