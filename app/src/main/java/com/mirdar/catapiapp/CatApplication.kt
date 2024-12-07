package com.mirdar.catapiapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CatApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}