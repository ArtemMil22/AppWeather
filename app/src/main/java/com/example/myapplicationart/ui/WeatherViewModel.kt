package com.example.myapplicationart.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplicationart.data.FunGetData
import com.example.myapplicationart.data.Repository
import com.example.myapplicationart.retrofit2RXjava.ApiRepository
import com.example.myapplicationart.retrofit2RXjava.ApiServiceRX
import com.example.myapplicationart.retrofit2RXjava.RepositotyRX
import com.example.myapplicationart.ui.models.WeatherModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class WeatherViewModel @Inject constructor(val apiServiceRX: ApiServiceRX)
    : ViewModel() {

    sealed class State {
        object LoadContent : State()
        data class ContentLoaded(
            val mainData: WeatherModel // MainData
        ) : State()
        object Error : State()
    }

    var repoRX: RepositotyRX = ApiRepository(apiServiceRX)
    var repo: FunGetData = Repository()
    val myWeatherList: MutableLiveData<State> = MutableLiveData()
    val myWeatherListRX = MutableSharedFlow<State>(
        1,0,BufferOverflow.DROP_OLDEST
    )

    fun getDataWeather(nameCity: String) {
        myWeatherList.value = State.LoadContent
        viewModelScope.launch {
            try {
                myWeatherList.value = State.ContentLoaded(repo.getWether(nameCity))
            } catch (e: Throwable) {
                myWeatherList.value = State.Error
                Log.e("MyLogogo", "Ой беда, караул", e)
            }
        }
    }

    fun getDataWeatherRX(){
        myWeatherListRX.tryEmit(State.LoadContent)
            repoRX.getWeatherRX()
                .doOnNext{ Log.d("Loge",it.toString())
                    myWeatherListRX.tryEmit(State.ContentLoaded(it))
                }
                .doOnError {
                    Log.e("Loge","Error RX",it)
                    myWeatherListRX.tryEmit(State.Error)
                }
                .subscribe()
    }
}