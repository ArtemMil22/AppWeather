package com.example.myapplicationart

import android.app.Application
import com.example.myapplicationart.di.AppComponent
import com.example.myapplicationart.di.DaggerAppComponent

class MyApplication : Application() {

//    val appComponent: AppComponent by lazy {
//        DaggerAppComponent.factory().create(applicationContext)
//    }

    companion object {
      //  lateinit var app: Application
        lateinit var appComponent: AppComponent
    }
    override fun onCreate() {
        //app = this
       appComponent = DaggerAppComponent.factory().create(this)
        super.onCreate()
    }
}