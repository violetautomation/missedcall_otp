package com.viol8.stgvirtual.application

import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDexApplication
import com.viol8.stgvirtual.koin.authModule
import com.viol8.stgvirtual.koin.homeModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ApplicationApp : MultiDexApplication() {
    companion object {
        lateinit var appInstance: ApplicationApp
    }

    override fun onCreate() {
        appInstance = this
        super.onCreate()
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        startKoin {
            androidContext(this@ApplicationApp)
            androidLogger(org.koin.core.logger.Level.NONE)
            modules(
                authModule, homeModule
            )
        }
    }
}