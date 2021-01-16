package com.viol8.stgvirtual.application

import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDexApplication


class ApplicationApp : MultiDexApplication() {
    companion object {
        lateinit var appInstance: ApplicationApp
    }

    override fun onCreate() {
        appInstance = this
        super.onCreate()
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }
}