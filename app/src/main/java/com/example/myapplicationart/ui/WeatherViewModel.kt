package com.example.myapplicationart.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplicationart.data.FunGetData
import com.example.myapplicationart.data.Repository
import com.example.myapplicationart.ui.models.WeatherModel
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    sealed class State {
        object LoadContent : State()
        data class ContentLoaded(
            val mainData: WeatherModel // MainData
        ) : State()
        object Error : State()
    }

    var repo: FunGetData = Repository()
    val myWeatherList: MutableLiveData<State> = MutableLiveData()

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
}