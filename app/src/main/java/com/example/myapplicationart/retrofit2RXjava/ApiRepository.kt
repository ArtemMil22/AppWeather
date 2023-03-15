package com.example.myapplicationart.retrofit2RXjava

import com.example.myapplicationart.ui.models.MainTemperature
import com.example.myapplicationart.ui.models.WeatherByHour
import com.example.myapplicationart.ui.models.WeatherIcon
import com.example.myapplicationart.ui.models.WeatherModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ApiRepository (private val serviceRX: ApiServiceRX) : RepositotyRX {

    override fun getWeatherRX(): Observable<WeatherModel> {
      return serviceRX.getDataRX()
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread()).map { mainData ->
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
}
