package com.example.myapplicationart.dagger2.rxretrofit

import com.example.myapplicationart.retrofit2RXjava.ApiRepository
import com.example.myapplicationart.retrofit2RXjava.ApiServiceRX
import com.example.myapplicationart.retrofit2RXjava.RepositotyRX
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {

    private val BASE_URL = "https://api.openweathermap.org/data/2.5/"

    @Provides
    fun provideApiServiceRX(retrofit: Retrofit): ApiServiceRX {
        return retrofit.create(ApiServiceRX::class.java)
    }

    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideRepo(serviceRX: ApiServiceRX): RepositotyRX = ApiRepository(serviceRX)
}
//    @Provides
//    fun provideAPI():ApiServiceRX{
//        val okHttpClient = OkHttpClient.Builder()
//        okHttpClient.connectTimeout(3, TimeUnit.MINUTES)
//
//        return Retrofit.Builder()
//            .baseUrl(BASE_URL)
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//            .addConverterFactory(GsonConverterFactory.create())
//            .client(okHttpClient.build())
//            .build()
//            .create(ApiServiceRX::class.java)
//    }
