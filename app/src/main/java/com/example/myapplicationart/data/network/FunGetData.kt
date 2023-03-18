package com.example.myapplicationart.data.network


import com.example.myapplicationart.ui.modelForUI.WeatherModel
import io.reactivex.Observable

interface FunGetData {
    suspend fun getWeather(nameCity:String): WeatherModel

    fun getWeatherRX(nameCity: String): Observable<WeatherModel>
}