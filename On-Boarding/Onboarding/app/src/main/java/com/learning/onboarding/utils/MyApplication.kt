package com.learning.onboarding.utils

import android.app.Application
import com.learning.onboarding.di.module
import org.koin.android.ext.koin.androidContext
import org.koin.android.logger.AndroidLogger
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            AndroidLogger()
            androidContext(this@MyApplication)
            modules(modules = module)
        }
    }
}