package com.example.myapplicationart.data.model

data class WeatherHours(
    val dt:Int,
    val main: Main,
    val weather: List<Weather>,
    val clouds: Clouds,
    val wind: Wind,
    val visibility:Int,
    val pop: Float,
    val sys: Sys,
    val dt_txt:String
)
