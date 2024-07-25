package com.example.nectarsupermarket

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NectarSupermarket: Application() {

    lateinit var context: Context

    override fun onCreate() {
        super.onCreate()

        context = applicationContext


    }
}