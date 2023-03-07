package com.example.myapplicationart.data

import com.example.myapplicationart.data.model.MainData
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("forecast?&appid=2c5cac32b3272b5a7f972d948b9bc0dd")
    suspend fun getWeatherData(@Query("q") cityName:String): MainData
}

//https://api.openweathermap.org/data/2.5/forecast?q=Tambov&appid=2c5cac32b3272b5a7f972d948b9bc0dd