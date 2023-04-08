package com.example.myapplicationart.ui

import com.example.myapplicationart.data.network.NetworkModule
import com.example.myapplicationart.di.PresentationDependencies
import com.example.myapplicationart.di.ViewModelModule
import dagger.Component
import javax.inject.Singleton

@[Singleton
Component(
    modules = [
        NetworkModule::class,
        ViewModelModule::class
    ],
    dependencies = [
        PresentationDependencies::class
    ]
)]
interface PresentationComponent {

    @Component.Factory
    interface Factory {
        fun create(
            dependencies: PresentationDependencies
        ): PresentationComponent
    }

    fun inject(fragment: FragmentWeather)
}