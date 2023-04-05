package com.example.myapplicationart.ui

import com.example.myapplicationart.data.network.NetworkModule
import com.example.myapplicationart.di.ViewModelModule
import com.example.myapplicationart.ui.di.PresentationDependencies
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

    fun inject(fragment: FragmentWeather)

    @Component.Factory
    interface Factory {
        fun create(
            dependencies: PresentationDependencies
        ): PresentationComponent
    }

}