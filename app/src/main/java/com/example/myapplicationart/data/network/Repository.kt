package com.example.myapplicationart.data.network

import com.example.myapplicationart.data.model.BASE_URL
import com.example.myapplicationart.ui.modelForUI.MainTemperature
import com.example.myapplicationart.ui.modelForUI.WeatherByHour
import com.example.myapplicationart.ui.modelForUI.WeatherIcon
import com.example.myapplicationart.ui.modelForUI.WeatherModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class Repository(private val apiServiceRX: ApiService?) : FunGetData {


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
    override suspend fun getWeather(nameCity: String): WeatherModel =
        api.getWeatherData(nameCity).let { mainData ->
            WeatherModel(
                citiName = mainData.city.name,
                weatherByHour = mainData.list.map {
                WeatherByHour(
                    mainTemp = MainTemperature(
                        temp = it.main.temp,
                        temp_min = it.main.temp_min,
                        temp_max = it.main.temp_max,
                        pressure = it.main.pressure,
                        humidity = it.main.humidity
                    ),
                    weatherIcon = mainData.list.map {
                        WeatherIcon(
                            icon = it.weather.first().icon,
                            description = it.weather.first().description.replaceFirstChar{it}
                        )
                    },
                    windSpeed = mainData.list.first().wind.speed,
                    dateTxt = mainData.list.first().dt_txt
                )
            })
        }

    override fun getWeatherRX(nameCity: String): Observable<WeatherModel> {
        return apiServiceRX?.getDataRX(nameCity)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.map { mainData ->
                WeatherModel(
                    citiName = mainData.city.name,
                    weatherByHour = mainData.list.map {
                        WeatherByHour(
                            mainTemp = MainTemperature(
                                temp = it.main.temp,
                                temp_min = it.main.temp_min,
                                temp_max = it.main.temp_max,
                                pressure = it.main.pressure,
                                humidity = it.main.humidity
                            ),
                            weatherIcon = mainData.list.map {
                                WeatherIcon(
                                    icon = it.weather.first().icon,
                                    description = it.weather.first().description.replaceFirstChar{it}
                                )
                            },
                            windSpeed = mainData.list.first().wind.speed,
                            dateTxt = mainData.list.first().dt_txt
                        )
                    })
            } ?: getWeatherRX("Thailand")
    }
}
