package com.example.myapplicationart.data.network


import com.example.myapplicationart.ui.modelForUI.CurrentWeatherData
import io.reactivex.Observable

interface FunGetData {
    suspend fun getWeather(nameCity:String): CurrentWeatherData

    fun getWeatherRX(nameCity: String): Observable<CurrentWeatherData>
}