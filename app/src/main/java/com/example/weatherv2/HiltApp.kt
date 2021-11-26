package com.example.weatherv2

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HiltApp: Application() {
    override fun onCreate() {
        super.onCreate()
        shared = this
    }

    companion object {
        lateinit var shared: HiltApp private set
    }
}