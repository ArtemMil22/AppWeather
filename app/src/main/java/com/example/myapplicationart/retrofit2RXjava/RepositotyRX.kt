package com.example.myapplicationart.retrofit2RXjava

import com.example.myapplicationart.ui.models.WeatherModel
import io.reactivex.Observable

interface RepositotyRX {

    fun getWeatherRX(): Observable<WeatherModel>
    // для конструктора класса передать зависимость, дергать функции для гет запроса, будем передавать инттерфейс во вью, а реализацию класса
}

//class RepositoryImpl @Inject constructor(val apiServiceRX: ApiServiceRX) :RepositotyRX{
//    override fun getWeatherRX(): Observable<WeatherModel> {
//        TODO("Not yet implemented")
//    }
