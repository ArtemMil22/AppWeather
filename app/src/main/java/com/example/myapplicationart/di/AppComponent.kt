package com.example.myapplicationart.di

import android.content.Context
import com.example.myapplicationart.di.module.AppModule
import com.example.myapplicationart.ui.di.PresentationDependencies
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@[Singleton
Component(
    modules = [
        AppModule::class
    ]
)]
interface AppComponent : PresentationDependencies {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context
        ): AppComponent
    }

}