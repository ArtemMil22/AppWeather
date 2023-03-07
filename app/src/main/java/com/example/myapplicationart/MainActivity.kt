package com.example.myapplicationart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplicationart.ui.FragmentWeather
import kotlinx.android.synthetic.main.fragment_weather.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        supportFragmentManager.beginTransaction().replace(
            R.id.placeHolder,
            FragmentWeather.newInstance())
            .commit()

//        supportFragmentManager
//            .beginTransaction()
//            .replace(
//                R.id.FramMain, HoursFragment.newInstance()
//            )
//            .commit()

    }
}