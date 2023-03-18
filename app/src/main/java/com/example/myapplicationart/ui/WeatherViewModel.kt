package com.example.myapplicationart.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplicationart.data.ApiService
import com.example.myapplicationart.data.FunGetData
import com.example.myapplicationart.data.Repository
import com.example.myapplicationart.ui.models.WeatherModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class WeatherViewModel @Inject constructor(val apiService: ApiService)
    : ViewModel() {

    sealed class State {
        object LoadContent : State()
        data class ContentLoaded(
            val mainData: WeatherModel
        ) : State()
        object Error : State()
    }

    var repoRX: FunGetData = Repository(apiService)
    var repo: FunGetData = Repository(null)
    val myWeatherList: MutableLiveData<State> = MutableLiveData()
    val myWeatherListRX = MutableSharedFlow<State>(
        1,0,BufferOverflow.DROP_OLDEST
    )

    fun getDataWeather(nameCity: String) {
        myWeatherList.value = State.LoadContent
        viewModelScope.launch {
            try {
                myWeatherList.value = State.ContentLoaded(repo.getWeather(nameCity).also{
                    var dataTxt = it.weatherByHour.first().dateTxt.substring(0, 10)
                })
            } catch (e: Throwable) {
                myWeatherList.value = State.Error
                Log.e("Loge", "Ой беда, караул", e)
            }
        }
    }

    fun getDataWeatherRX(nameCity: String){
        myWeatherListRX.tryEmit(State.LoadContent)
            repoRX.getWeatherRX(nameCity)
                .doOnNext{
                    Log.d("Loge1","Функция getRX работает ")
                    myWeatherListRX.tryEmit(State.ContentLoaded(it.also{
                        var dataTxt = it.weatherByHour.first().dateTxt.substring(0, 10)
                    }))
                }
                .doOnError {
                    Log.e("Loge1","Error RX",it)
                    myWeatherListRX.tryEmit(State.Error)
                }
                .subscribe()
    }
}