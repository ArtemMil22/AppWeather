package com.example.myapplicationart.di

import android.content.Context
import com.example.myapplicationart.ui.FragmentWeather
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@[Singleton
Component( modules = [NetworkModule::class, ViewModelModule::class]
)]
interface AppComponent {

//    fun getWeatherDialog(): WeatherDialog
    fun inject(fragWeather: FragmentWeather)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

}