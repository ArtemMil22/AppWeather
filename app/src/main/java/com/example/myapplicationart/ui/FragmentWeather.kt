package com.example.myapplicationart.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplicationart.DialogManager
import com.example.myapplicationart.R
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_weather.*
import kotlinx.android.synthetic.main.fragment_weather.view.*


class FragmentWeather : Fragment(R.layout.fragment_weather) {

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: WeatherAdapter
    lateinit var collapsingToolbarLayout: CollapsingToolbarLayout
    lateinit var toolBar: Toolbar
    lateinit var appBarLayout: AppBarLayout
    lateinit var collapsingMenu: Menu

    private val model: WeatherViewModel by activityViewModels()

    companion object {
        fun newInstance() = FragmentWeather()
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collapsingToolbarLayout = view.findViewById(R.id.collapsing_toolbar)
        toolBar = view.findViewById(R.id.toolbar)
        appBarLayout = view.findViewById(R.id.appBarLayout)
        getAppBar()
        toolBar.inflateMenu(R.menu.fragment_weather)

//        setHasOptionsMenu(true)

        recyclerView = view.tvRv
        model.getDataWeather("Tambov")
        adapter = WeatherAdapter()
        recyclerView.adapter = adapter
        model.myWeatherList.observe(viewLifecycleOwner, ::render)

        val buttonSearch = view.findViewById<ImageButton>(R.id.buttonSearch)
        val buttonSearch2 = view.findViewById<ImageButton>(R.id.buttonSearch2)

        buttonSearch2.setOnClickListener {
            val dialogFragment = WeatherDialog()
            dialogFragment.show(childFragmentManager, "My Fragment")

        }
        setFragmentResultListener("key") { key, bundle ->
            val result = bundle.getString("bundleKey")
            Log.d("Loge",result.toString())
            model.getDataWeather(result.toString())
        }

        buttonSearch.setOnClickListener {
            DialogManager.showAlertDialog(
                requireContext(), object : DialogManager.Listener {
                    override fun onClick(name: String?) {
                        name?.let { it -> model.getDataWeather(it) }
                    }
                })
//                DialogManager.searchByNameDialog(
//                    requireContext(), object : DialogManager.Listener {
//                        override fun onClick(name: String?) {
//                            name?.let { it1 -> getWeather(it1) }
//                        }
//                    })
        }

    }


    private fun render(state: WeatherViewModel.State) {
        when (state) {
            is WeatherViewModel.State.ContentLoaded -> {
                tvData.text = state.mainData.weatherByHour.first().dateTxt.substring(0, 10)
                tvSunny.text = state.mainData.weatherByHour.first().weatherIcon.first().description
                tvTemper.text =
                    state.mainData.weatherByHour.first().mainTemp.temp.toInt().toString()
                        .let { it + "°C" }
                tvMinMax.text =
                    state.mainData.weatherByHour.first().mainTemp.let { "Min ${it.temp_min.toInt()}°/Max ${it.temp_max.toInt()}°" }
                textView.text = state.mainData.citiName
                Picasso.get()
                    .load("https://openweathermap.org/img/wn/" + state.mainData.weatherByHour.first().weatherIcon.first().icon + "@2x.png")
                    .into(tvIcone)
                adapter.setList(state.mainData.weatherByHour)
            }
            WeatherViewModel.State.Error -> "dadad"
            WeatherViewModel.State.LoadContent -> "dasdddda"
        }
    }

    fun getAppBar() {
        appBarLayout.addOnOffsetChangedListener(object : OnOffsetChangedListener {
            var isShow = false
            var scrollRange = -1
            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.title =
                        "Title" // Careful! There should be a space between double quote. Otherwise it won't work.
                    isShow = false
                } else if (!isShow) {
                    collapsingToolbarLayout.title = ""
                    isShow = true
                }
            }
        })
    }
}

//
//private fun render(state: WeatherViewModel.State) {
//    when(state) {
//        is WeatherViewModel.State.ContentLoaded -> {
//            tvData.text = state.mainData.list.first().dt_txt.substring(0,10)
//            tvSunny.text = state.mainData.list.first().weather.first().description.capitalize()
//            tvTemper.text = state.mainData.list.first().main.temp.let{it - 273.15f}.toInt().toString().let{it+"°C"}
//            tvMinMax.text = state.mainData.list.first().main.let{"Min ${it.temp_min.let{it - 273.15f}.toInt()}°/Max ${it.temp_max.let{it - 273.15f}.toInt()}°"}
//            textView.text = state.mainData.city.name
//            Picasso.get()
//                .load("https://openweathermap.org/img/wn/" + state.mainData.list.first().weather.first().icon.toString() +"@2x.png")
//                .into(tvIcone)
//
//            adapter.setList(state.mainData.list)
//        }
//        WeatherViewModel.State.Error -> TODO()
//        WeatherViewModel.State.LoadContent -> TODO()
//    }
//}




