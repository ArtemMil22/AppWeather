package com.example.myapplicationart.ui.modelForUI

data class CurrentWeatherData(
    val dataText: String,
    val weatherIconDescriptionToday: String,
    val tempToday: String,
    val maxAndMinTempToday: MaxMinTemp,
    val cityName: String,
    val iconUrl: String,
    val weatherByHour: List<WeatherByHour>
)

data class MaxMinTemp(
    val maxTempToday: Int,
    val minTempToday: Int
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

