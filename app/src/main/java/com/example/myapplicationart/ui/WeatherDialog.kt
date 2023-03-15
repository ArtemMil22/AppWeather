package com.example.myapplicationart.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.example.myapplicationart.R
import javax.inject.Inject


class WeatherDialog @Inject constructor() : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_weather_dialog, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonCity: Button = view.findViewById(R.id.btnPositive)
        val editText: EditText = view.findViewById(R.id.tvTitle)
        var resultString:String

        buttonCity.setOnClickListener {
            resultString = editText.text.toString()
            setFragmentResult(
                "key", bundleOf("bundleKey" to resultString)
            )
            Log.d("Loge",resultString)
            dismiss()
        }
    }

    companion object {
        fun newInstance() = WeatherDialog()
    }

}



