package com.example.myapplicationart.data.network

import com.example.myapplicationart.common.mapToCurrentWeatherData
import com.example.myapplicationart.domain.Repository
import com.example.myapplicationart.ui.modelForUI.CurrentWeatherData
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RepositoryImp @Inject constructor(
    private val apiService: ApiService
) : Repository {

    override suspend fun getWeather(nameCity: String): CurrentWeatherData =
        apiService.getWeatherData(nameCity).mapToCurrentWeatherData()

    override fun getWeatherRX(nameCity: String): Observable<CurrentWeatherData> {
        return apiService.getDataRX(nameCity)
            .map { mainData ->
                mainData.mapToCurrentWeatherData()
            }
            .subscribeOn(Schedulers.io())
    }
}
