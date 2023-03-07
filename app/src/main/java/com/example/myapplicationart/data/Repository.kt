package com.example.myapplicationart.data

import com.example.myapplicationart.ui.models.MainTemperature
import com.example.myapplicationart.ui.models.WeatherByHour
import com.example.myapplicationart.ui.models.WeatherIcon
import com.example.myapplicationart.ui.models.WeatherModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class Repository : FunGetData {

    private val retrofit by lazy {
        Retrofit.Builder().baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create()).build()
    }
    private val api: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
    override suspend fun getWether(nameCity: String): WeatherModel =
        api.getWeatherData(nameCity).let { mainData ->
            WeatherModel(
                citiName = mainData.city.name,
                weatherByHour = mainData.list.map {
                WeatherByHour(
                    mainTemp = MainTemperature(
                        temp = it.main.temp.let{it - 273.15f},
                        temp_min = it.main.temp_min.let {it - 273.15f},
                        temp_max = it.main.temp_max.let{it - 273.15f},
                        pressure = it.main.pressure,
                        humidity = it.main.humidity
                    ),
                    weatherIcon = mainData.list.map {
                        WeatherIcon(
                            icon = it.weather.first().icon,
                            description = it.weather.first().description.capitalize()
                        )
                    },
                    windSpeed = mainData.list.first().wind.speed,
                    dateTxt = mainData.list.first().dt_txt
                )
            })
        }
}
