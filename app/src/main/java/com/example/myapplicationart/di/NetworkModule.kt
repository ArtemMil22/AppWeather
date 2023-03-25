package com.example.myapplicationart.di

import com.example.myapplicationart.data.network.ApiService
import com.example.myapplicationart.data.network.RepositoryImp
import com.example.myapplicationart.domain.BASE_URL
import com.example.myapplicationart.domain.Repository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {

    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideRepo(service: ApiService): Repository = RepositoryImp(service)

}

