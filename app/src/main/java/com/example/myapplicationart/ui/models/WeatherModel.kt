package com.example.myapplicationart.ui.models

data class WeatherModel(
    val citiName: String,
    val weatherByHour: List<WeatherByHour>
)

data class WeatherByHour(
    val mainTemp: MainTemperature,
    val weatherIcon: List<WeatherIcon>,
    val windSpeed: Float,
    val dateTxt: String
)

data class MainTemperature(
    val temp:Float,
    val temp_min:Float,
    val temp_max:Float,
    val pressure:Int,
    val humidity:Int,
)

data class WeatherIcon(
    val description:String,
    val icon:String
)

//val weather: List<Weather>,
//val clouds: Clouds,
//val wind: Wind,
//val visibility:Int,
//val dt_txt:String
