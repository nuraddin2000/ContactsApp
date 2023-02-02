package com.vholodynskyi.assignment

import android.app.Application
import com.vholodynskyi.assignment.di.apiModule
import com.vholodynskyi.assignment.di.roomModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        application = this
        startKoin {
            androidContext(this@App)
            modules(apiModule,roomModule)
        }
    }

    companion object {
        lateinit var application: Application
    }
}