package com.example.myapplicationart

import android.app.Application
import com.example.myapplicationart.data.dagger2.AppComponent
import com.example.myapplicationart.data.dagger2.DaggerAppComponent

class WeatherApp : Application() {

    companion object {
        lateinit var app: Application
        lateinit var component: AppComponent
    }

    /**
     * С даггером будем разбираться потом щас сделаем просто заглушку что бы собиралось
     */

    override fun onCreate() {
        app = this
        component = DaggerAppComponent.factory().create(this)

        super.onCreate()
    }
}