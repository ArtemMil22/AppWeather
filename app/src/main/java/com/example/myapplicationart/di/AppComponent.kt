package com.example.myapplicationart.di

import android.content.Context
import com.example.myapplicationart.data.network.NetworkModule
import com.example.myapplicationart.ui.PresentationComponent
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@[Singleton
Component(
    modules = [NetworkModule::class, ViewModelModule::class, AppSubcomponents::class]
)]
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun presentationComponent(): PresentationComponent.Factory

}