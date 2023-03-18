package com.example.myapplicationart.data


import com.example.myapplicationart.ui.models.WeatherModel
import io.reactivex.Observable

interface FunGetData {
    suspend fun getWeather(nameCity:String): WeatherModel

    fun getWeatherRX(nameCity: String): Observable<WeatherModel>
}