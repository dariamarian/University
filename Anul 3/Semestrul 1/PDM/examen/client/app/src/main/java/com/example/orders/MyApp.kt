package com.example.orders

import android.app.Application
import android.util.Log
import com.example.orders.core.TAG

class MyApp : Application() {
    lateinit var container: AppContainer

    companion object {
        lateinit var instance: MyApp
            private set
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "init")
        container = AppContainer(this)
    }
}
