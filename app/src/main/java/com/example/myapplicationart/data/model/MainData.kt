package com.example.myapplicationart.data.model

data class MainData(
    val cod:String,
    val message: Int,
    val cnt: Int,
    val list: List<WeatherHours>,
    val city: City
)
