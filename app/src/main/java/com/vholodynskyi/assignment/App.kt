package com.vholodynskyi.assignment

import android.app.Application
import android.content.Context
import com.vholodynskyi.assignment.di.GlobalFactory

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        GlobalFactory.init(this)
        application = this
    }

    companion object {
        lateinit var application: Application
    }
}