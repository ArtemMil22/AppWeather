package com.example.myapplicationart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplicationart.ui.FragmentWeather

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().replace(
            R.id.placeHolder,
            FragmentWeather.newInstance())
            .commit()

    }
}