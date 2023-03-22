package com.example.myapplicationart.data.dagger2

import android.content.Context
import com.example.myapplicationart.ui.FragmentWeather
import com.example.myapplicationart.ui.WeatherDialog
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@[Singleton
Component(
    modules = [
        NetworkModule::class,
        ViewModelModule::class
    ]
)]
interface AppComponent {

    fun getWeatherDialog(): WeatherDialog
    fun inject(fragWeather: FragmentWeather)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

}