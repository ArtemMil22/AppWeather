package com.example.myapplicationart

import android.app.Application
import com.example.myapplicationart.di.AppComponent
import com.example.myapplicationart.di.DaggerAppComponent

class MyApplication : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        appComponent = DaggerAppComponent
            .factory()
            .create(this)

        super.onCreate()
    }

}