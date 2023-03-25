package com.example.myapplicationart.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplicationart.R
import com.example.myapplicationart.ResourcesProvider
import com.example.myapplicationart.data.network.RepositoryImp
import com.example.myapplicationart.domain.TAG
import com.example.myapplicationart.ui.modelForUI.WeatherByHour
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class WeatherViewModel @Inject constructor(
    private val repositoryImp: RepositoryImp,
    private val resourcesProvider: ResourcesProvider
) : ViewModel() {

    sealed class State {
        object LoadContent : State()
        data class ContentLoaded(
            val dataText: String,
            val weatherIconDescriptionToday: String,
            val tempToday: String,
            val maxAndMinTempToday: String,
            val cityName: String,
            val iconUrl: String,
            val weatherByHour: List<WeatherByHour>
        ) : State()

        object Error : State()
    }

    val myWeatherList: MutableLiveData<State> = MutableLiveData()
    val myWeatherListRX = MutableSharedFlow<State>(
        1, 0, BufferOverflow.DROP_OLDEST
    )

    fun getDataWeather(nameCity: String) {
        myWeatherList.value = State.LoadContent
        viewModelScope.launch {
            try {
                repositoryImp.getWeather(nameCity)
                    .let { weatherData ->
                        myWeatherList.value = State.ContentLoaded(
                            dataText = weatherData.dataText.substring(0, 10),
                            weatherIconDescriptionToday = weatherData.weatherIconDescriptionToday,
                            tempToday = weatherData.tempToday,
                            maxAndMinTempToday = resourcesProvider.provideStringResourcesWithArgs(
                                R.string.extremum,
                                weatherData.maxAndMinTempToday.minTempToday,
                                weatherData.maxAndMinTempToday.maxTempToday
                            ),
                            cityName = weatherData.cityName,
                            iconUrl = resourcesProvider.provideStringResourcesWithArgs(
                                R.string.image_url,
                                weatherData.iconUrl
                            ),
                            weatherByHour = weatherData.weatherByHour
                        )

                    }

            } catch (e: Throwable) {
                myWeatherList.value = State.Error
                Log.e(TAG, "Request city by using coroutines Error", e)
            }
        }
    }

    fun getDataWeatherRX(nameCity: String) {
        myWeatherListRX.tryEmit(State.LoadContent)
        repositoryImp.getWeatherRX(nameCity)
            .map { weatherData ->
                Log.d(TAG, "Request city by using RXJava works properly")
                myWeatherListRX.tryEmit(
                    State.ContentLoaded(
                        dataText = weatherData.dataText.substring(0, 10),
                        weatherIconDescriptionToday = weatherData.weatherIconDescriptionToday,
                        tempToday = weatherData.tempToday,
                        maxAndMinTempToday = resourcesProvider.provideStringResourcesWithArgs(
                            R.string.extremum,
                            weatherData.maxAndMinTempToday.minTempToday,
                            weatherData.maxAndMinTempToday.maxTempToday
                        ),
                        cityName = weatherData.cityName,
                        iconUrl = resourcesProvider.provideStringResourcesWithArgs(
                            R.string.image_url,
                            weatherData.iconUrl
                        ),
                        weatherByHour = weatherData.weatherByHour
                    )
                )
            }
            .doOnError {
                Log.e(TAG, "Request city by using RXJava Error", it)
                myWeatherListRX.tryEmit(State.Error)
            }
            .subscribe()
    }
}