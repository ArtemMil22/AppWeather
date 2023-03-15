package com.example.myapplicationart.retrofit2RXjava

import com.example.myapplicationart.data.model.MainData
import io.reactivex.Observable
import retrofit2.http.GET

interface ApiServiceRX {

    @GET ("forecast?q=Tambov&appid=2c5cac32b3272b5a7f972d948b9bc0dd")
    fun getDataRX(): Observable<MainData>
}