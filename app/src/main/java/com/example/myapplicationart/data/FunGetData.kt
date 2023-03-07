package com.example.myapplicationart.data


import com.example.myapplicationart.ui.models.WeatherModel

interface FunGetData {
    suspend fun getWether(nameCity:String): WeatherModel
}