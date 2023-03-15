package com.example.myapplicationart.dagger2

import com.example.myapplicationart.dagger2.rxretrofit.NetworkModule
import com.example.myapplicationart.ui.FragmentWeather
import com.example.myapplicationart.ui.ViewModelModule
import com.example.myapplicationart.ui.WeatherDialog
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class,ViewModelModule::class])
interface AppComponent {
//    fun getCar():Car
//    fun getEngine():Engine
//    fun getFuel():Fuel
    fun getWeatherDialog():WeatherDialog
    fun inject(fragWeather: FragmentWeather)
}