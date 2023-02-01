package com.vholodynskyi.assignment

import android.app.Application
import android.content.Context
import com.vholodynskyi.assignment.di.GlobalFactory
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        application = this
        startKoin {
            androidContext(this@App)
            modules()
        }
    }

    companion object {
        lateinit var application: Application
    }
}