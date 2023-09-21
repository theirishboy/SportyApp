package com.example.yoursportapp.android

import android.app.Application

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

//        startKoin {
//            androidContext(this@MainApplication)
//            androidLogger()
//            modules(appModule())
//        }
    }
}