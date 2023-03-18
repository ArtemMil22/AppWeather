package com.example.myapplicationart.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplicationart.R
import com.example.myapplicationart.data.model.ICON_URL_PART_ONE
import com.example.myapplicationart.data.model.ICON_URL_PART_TWO
import com.example.myapplicationart.ui.modelForUI.WeatherByHour
import com.squareup.picasso.Picasso


class WeatherAdapter : RecyclerView.Adapter<WeatherAdapter.WeatherHolder>() {

    var listWeather = emptyList<WeatherByHour>()

    class WeatherHolder(view: View) : RecyclerView.ViewHolder(view) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            WeatherHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_weather_layout, parent, false)

        return WeatherHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherHolder, position: Int) {

        var tvDataItem = holder.itemView.findViewById<TextView>(R.id.tvDataItem)
        var tvSunnyItem = holder.itemView.findViewById<TextView>(R.id.tvSunnyItem)
        var tvTempItem = holder.itemView.findViewById<TextView>(R.id.tvTempItem)
        var tvVeterItem = holder.itemView.findViewById<TextView>(R.id.tvVeterItem)
        var tvIm = holder.itemView.findViewById<ImageView>(R.id.tvIm)

        tvDataItem.text = listWeather[position].dateTxt.let { it.substring(11, 16) }
        tvSunnyItem.text = listWeather[position].weatherIcon.first().description
        tvTempItem.text = listWeather[position].mainTemp.temp.toInt().toString().let { it + "°C" }
        tvVeterItem.text = listWeather[position].windSpeed.toInt().toString().let { it + " m/s" }
        Picasso.get()
            .load(ICON_URL_PART_ONE + listWeather[position].weatherIcon.first().icon + ICON_URL_PART_TWO)
            .into(tvIm)
    }

    override fun getItemCount(): Int {
        return listWeather.size
    }

    fun setList(getList: List<WeatherByHour>) {
        listWeather = getList
        notifyDataSetChanged()
    }

}
