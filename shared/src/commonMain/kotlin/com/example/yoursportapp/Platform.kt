package com.example.yoursportapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform