package com.ilyaushenin.jpmorganchase

import android.app.Application
import com.ilyaushenin.jpmorganchase.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class JPApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@JPApplication)
            modules(appModule)
        }
    }
}