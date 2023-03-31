package com.example.myapplicationart.ui

import dagger.Subcomponent


@Subcomponent
interface PresentationComponent {

    @Subcomponent.Factory
    interface Factory{
        fun create():PresentationComponent
    }

    fun inject(fragment:FragmentWeather)
}