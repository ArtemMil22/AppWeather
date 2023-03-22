package com.example.myapplicationart.data.network

import com.example.myapplicationart.domain.BASE_URL
import com.example.myapplicationart.ui.modelForUI.*
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class Repository @Inject constructor(
    private val apiServiceRX: ApiService
) : FunGetData {


    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    private val api: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    override suspend fun getWeather(nameCity: String): CurrentWeatherData =
        api.getWeatherData(nameCity).let { mainData ->
            val firstDay = mainData.list.first()
            CurrentWeatherData(
                dataText = firstDay.dt_txt,
                weatherIconDescriptionToday = firstDay.weather.first().description.replaceFirstChar { it },
                iconUrl = firstDay.weather.first().icon,
                tempToday = firstDay.main.temp.toInt().toString(),
                maxAndMinTempToday = MaxMinTemp(
                    maxTempToday = firstDay.main.temp_max.toInt(),
                    minTempToday = firstDay.main.temp_min.toInt()
                ),
                cityName = mainData.city.name,
                weatherByHour = mainData.list.map {
                    WeatherByHour(
                        mainTemp = MainTemperature(
                            temp = it.main.temp,
                            temp_min = it.main.temp_min,
                            temp_max = it.main.temp_max,
                            pressure = it.main.pressure,
                            humidity = it.main.humidity
                        ),
                        weatherIcon = mainData.list.map { weatherByHours ->
                            weatherByHours.weather
                                .first()
                                .let { weather ->
                                    WeatherIcon(
                                        icon = weather.icon,
                                        description = weather.description.replaceFirstChar { it }
                                    )
                                }

                        },
                        windSpeed = mainData.list.first().wind.speed,
                        dateTxt = mainData.list.first().dt_txt
                    )
                }
            )
        }

    /**
     * Эта часть требует доработки и вынесения общего кода в отдельную функцию
    **/
    override fun getWeatherRX(nameCity: String): Observable<CurrentWeatherData> {
        return apiServiceRX.getDataRX(nameCity)
            .map { mainData ->
                val firstDay = mainData.list.first()
                CurrentWeatherData(
                    dataText = firstDay.dt_txt,
                    weatherIconDescriptionToday = firstDay.weather.first().description.replaceFirstChar { it },
                    iconUrl = firstDay.weather.first().icon,
                    tempToday = firstDay.main.temp.toInt().toString(),
                    maxAndMinTempToday = MaxMinTemp(
                        maxTempToday = firstDay.main.temp_max.toInt(),
                        minTempToday = firstDay.main.temp_min.toInt()
                    ),
                    cityName = mainData.city.name,
                    weatherByHour = mainData.list.map {
                        WeatherByHour(
                            mainTemp = MainTemperature(
                                temp = it.main.temp,
                                temp_min = it.main.temp_min,
                                temp_max = it.main.temp_max,
                                pressure = it.main.pressure,
                                humidity = it.main.humidity
                            ),
                            weatherIcon = mainData.list.map { weatherByHours ->
                                weatherByHours.weather
                                    .first()
                                    .let { weather ->
                                        WeatherIcon(
                                            icon = weather.icon,
                                            description = weather.description.replaceFirstChar { it }
                                        )
                                    }

                            },
                            windSpeed = mainData.list.first().wind.speed,
                            dateTxt = mainData.list.first().dt_txt
                        )
                    }
                )
            }
            .subscribeOn(Schedulers.io())
    }
}
