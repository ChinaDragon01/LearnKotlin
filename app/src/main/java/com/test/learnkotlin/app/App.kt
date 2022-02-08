package com.test.learnkotlin.app

import android.app.Application
import android.content.Context
import com.test.learnkotlin.utils.GlobalCrashHandler

class App : Application() {

    companion object {
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this
        GlobalCrashHandler.getInstance().registerContext(this)
    }

}