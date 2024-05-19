package com.yajhz

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.yajhz.di.component.AppComponent
import com.yajhz.di.component.DaggerAppComponent
import com.yajhz.utils.AppLogger

class MvvmApp : Application() {
    var appComponent: AppComponent? = null

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        if (instance == null) {
            instance = this
        }
        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
        appComponent!!.inject(this)
        AppLogger.init()
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        var instance: MvvmApp? = null
            private set

        @SuppressLint("StaticFieldLeak")
        var context: Context? = null

        var currentLat: Double = 30.0444
        var currentLong: Double = 31.2357

    }
}
