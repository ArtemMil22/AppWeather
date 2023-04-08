package com.example.myapplicationart.common

import com.example.myapplicationart.data.model.MainData
import com.example.myapplicationart.ui.modelForUI.*

fun MainData.mapToCurrentWeatherData(): CurrentWeatherData {
    val mainData = this.list.first()
    return CurrentWeatherData(
        dataText = mainData.dt_txt,
        weatherIconDescriptionToday = mainData.weather.first().description.replaceFirstChar { it },
        tempToday = mainData.main.temp.toInt().toString().let { it + "°C" },
        maxAndMinTempToday = MaxMinTemp(
            maxTempToday = mainData.main.temp_max.toInt(),
            minTempToday = mainData.main.temp_min.toInt()
        ),
        cityName = this.city.name,
        iconUrl = mainData.weather.first().icon,
        weatherByHour = this.list.map {
            WeatherByHour(
                mainTemp = this.getMainTemp(),
                weatherIcon = this.list.map {
                    this.getWeatherIcon()
                },
                windSpeed = mainData.wind.speed,
                dateTxt = mainData.dt_txt
            )
        }
    )
}

fun MainData.getMainTemp(): MainTemperature {
    val mainData = this.list.first().main
    return MainTemperature(
        temp = mainData.temp,
        temp_min = mainData.temp_min,
        temp_max = mainData.temp_max,
        pressure = mainData.pressure,
        humidity = mainData.humidity
    )
}

fun MainData.getWeatherIcon(): WeatherIcon =
    WeatherIcon(
        description = this.list.first().weather.first().description,
        icon = this.list.first().weather.first().icon,
    )