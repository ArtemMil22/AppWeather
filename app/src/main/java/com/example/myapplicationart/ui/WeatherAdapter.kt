package com.example.myapplicationart.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplicationart.R
import com.example.myapplicationart.ui.models.WeatherByHour
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_weather_layout.view.*


class WeatherAdapter:RecyclerView.Adapter<WeatherAdapter.WeatherHolder>() {

    var listWeather = emptyList<WeatherByHour>()



    class WeatherHolder(view:View):RecyclerView.ViewHolder(view){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            WeatherHolder {
    val view = LayoutInflater.from(parent.context)
        .inflate(R.layout.item_weather_layout,parent,false)
    return WeatherHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherHolder, position: Int) {
        holder.itemView.tvDataItem.text = listWeather[position].dateTxt.let{it.substring(11,16)}
        holder.itemView.tvSunnyItem.text = listWeather[position].weatherIcon.first().description
        holder.itemView.tvTempItem.text = listWeather[position].mainTemp.temp.toInt().toString().let{it+"°C"}
        holder.itemView.tvVeterItem.text = listWeather[position].windSpeed.toInt().toString().let{it+" m/s"}
        Picasso.get()
            .load("https://openweathermap.org/img/wn/" + listWeather[position].weatherIcon.first().icon +"@2x.png")
            .into(holder.itemView.tvIm)
    }
    override fun getItemCount(): Int {
        return listWeather.size
    }

    fun setList(getList:List<WeatherByHour>){
        listWeather = getList
        notifyDataSetChanged()
    }


}

//        Picasso.get().load("http://openweathermap.org/img/wn/10d@2x.png")
//            .into(holder.itemView.tvIm)
//http://openweathermap.org/img/wn/10d@2x.png    "http://openweathermap.org/img/wn/" + listWeather[position].weather.first().icon +"@2x.png"
//
//override fun onBindViewHolder(holder: WeatherAdapter.WeatherHolder, position: Int) {
//    holder.itemView.tvDataItem.text = listWeather[position].dt_txt.let{it.substring(11,16)}
//    holder.itemView.tvSunnyItem.text = listWeather[position].weather.first().description.capitalize()
//    holder.itemView.tvTempItem.text = listWeather[position].main.temp.let{it - 273.15f}.toInt().toString().let{it+"°C"}
//    holder.itemView.tvVeterItem.text = listWeather[position].wind.speed.toInt().toString().let{it+" m/s"}
//    Picasso.get()
//        .load("https://openweathermap.org/img/wn/" + listWeather[position].weather.first().icon.toString() +"@2x.png")
//        .into(holder.itemView.tvIm)
//}