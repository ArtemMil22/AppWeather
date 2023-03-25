package com.example.myapplicationart.domain


import com.example.myapplicationart.ui.modelForUI.CurrentWeatherData
import io.reactivex.Observable

interface Repository {
    suspend fun getWeather(nameCity:String): CurrentWeatherData

    fun getWeatherRX(nameCity: String): Observable<CurrentWeatherData>
}