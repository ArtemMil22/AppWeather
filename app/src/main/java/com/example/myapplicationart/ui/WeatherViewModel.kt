package com.example.myapplicationart.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplicationart.data.model.TAG
import com.example.myapplicationart.data.network.ApiService
import com.example.myapplicationart.data.network.FunGetData
import com.example.myapplicationart.data.network.Repository
import com.example.myapplicationart.ui.modelForUI.WeatherModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class WeatherViewModel @Inject constructor(apiService: ApiService)
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
                myWeatherList.value = State.ContentLoaded(repo.getWeather(nameCity))
            } catch (e: Throwable) {
                myWeatherList.value = State.Error
                Log.e(TAG, "Request city by using coroutines Error", e)
            }
        }
    }

    fun getDataWeatherRX(nameCity: String){
        myWeatherListRX.tryEmit(State.LoadContent)
            repoRX.getWeatherRX(nameCity)
                .doOnNext{
                    Log.d(TAG,"Request city by using RXJava works properly")
                    myWeatherListRX.tryEmit(State.ContentLoaded(it))
                }
                .doOnError {
                    Log.e(TAG,"Request city by using RXJava Error",it)
                    myWeatherListRX.tryEmit(State.Error)
                }
                .subscribe()
    }
}