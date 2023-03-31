package com.example.myapplicationart.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplicationart.R
import com.example.myapplicationart.data.model.WeatherHours
import com.example.myapplicationart.domain.ICON_URL_PART_ONE
import com.example.myapplicationart.domain.ICON_URL_PART_TWO
import com.squareup.picasso.Picasso
import javax.inject.Inject


class WeatherAdapter @Inject constructor() : RecyclerView.Adapter<WeatherAdapter.WeatherHolder>() {

    private var listWeather = emptyList<WeatherHours>()

    class WeatherHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            WeatherHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_weather_layout, parent, false)

        return WeatherHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherHolder, position: Int) {

        val tvDataItem = holder.itemView.findViewById<TextView>(R.id.tvDataItem)
        val tvSunnyItem = holder.itemView.findViewById<TextView>(R.id.tvSunnyItem)
        val tvTempItem = holder.itemView.findViewById<TextView>(R.id.tvTempItem)
        val tvWindItem = holder.itemView.findViewById<TextView>(R.id.tvVeterItem)
        val tvIm = holder.itemView.findViewById<ImageView>(R.id.tvIm)

        tvDataItem.text = listWeather[position].dt_txt.substring(11, 16)
        tvSunnyItem.text = listWeather[position].weather.first().description
        tvTempItem.text = listWeather[position].main.temp.toInt().toString().let { "$it°C" }
        tvWindItem.text = listWeather[position].wind.speed.toInt().toString().let { "$it m/s" }
        Picasso.get()
            .load(ICON_URL_PART_ONE + listWeather[position].weather.first().icon + ICON_URL_PART_TWO)
            .into(tvIm)
    }

    override fun getItemCount(): Int {
        return listWeather.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(getList: List<WeatherHours>) {
        listWeather = getList
        notifyDataSetChanged()
    }
}
