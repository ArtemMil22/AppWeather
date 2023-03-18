package com.example.myapplicationart.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplicationart.R
import com.example.myapplicationart.dagger2.AppComponent
import com.example.myapplicationart.dagger2.DaggerAppComponent
import com.example.myapplicationart.dagger2.ViewModelFactory
import com.example.myapplicationart.data.ApiService
import com.example.myapplicationart.data.model.ICON_URL_PART_ONE
import com.example.myapplicationart.data.model.ICON_URL_PART_TWO
import com.example.myapplicationart.data.model.TAG
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.squareup.picasso.Picasso
import javax.inject.Inject

class FragmentWeather : Fragment(R.layout.fragment_weather) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: WeatherAdapter
    private lateinit var collapsingToolbarLayout: CollapsingToolbarLayout
    private lateinit var toolBar: Toolbar
    private lateinit var appBarLayout: AppBarLayout
    private lateinit var dialogFragment: WeatherDialog
    private lateinit var appComponent: AppComponent
    private lateinit var buttonSearch: ImageButton
    private lateinit var buttonSearch2: ImageButton

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var api: ApiService

    private val model: WeatherViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[WeatherViewModel::class.java]
    }

    override fun onAttach(context: Context) {
        appComponent = DaggerAppComponent.create()
        appComponent.inject(this)
        super.onAttach(context)
    }

    companion object {
        fun newInstance() = FragmentWeather()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonSearch = view.findViewById(R.id.buttonSearch)
        buttonSearch2 = view.findViewById(R.id.buttonSearch2)
        collapsingToolbarLayout = view.findViewById(R.id.collapsing_toolbar)
        toolBar = view.findViewById(R.id.toolbar)
        appBarLayout = view.findViewById(R.id.appBarLayout)
        getAppBar()
        toolBar.inflateMenu(R.menu.fragment_weather)
        recyclerView = view.findViewById(R.id.tvRv)
        model.getDataWeather("Tambov")
        adapter = WeatherAdapter()
        recyclerView.adapter = adapter
        model.myWeatherList.observe(viewLifecycleOwner, ::render)

        buttonSearch2.setOnClickListener {
            dialogFragment = appComponent.getWeatherDialog()
            dialogFragment.show(parentFragmentManager, "My Fragment")
        }

        buttonSearch.setOnClickListener {
            DialogManager.showAlertDialog(
                requireContext(), object : DialogManager.Listener {
                    override fun onClick(name: String?) {
                        name?.let { it -> model.getDataWeather(it) }
                    }
                })
        }
    }

    override fun onResume() {
        super.onResume()
        setFragmentResultListener("key") { _, bundle ->
            val result = bundle.getString("bundleKey")
            Log.d("Loge1", result.toString())
            model.getDataWeather(result.toString())
        }
    }

    private fun render(state: WeatherViewModel.State) {
        val tvData = view?.findViewById<TextView>(R.id.tvData)
        val tvSunny = view?.findViewById<TextView>(R.id.tvSunny)
        val tvTemper = view?.findViewById<TextView>(R.id.tvTemper)
        val tvMinMax = view?.findViewById<TextView>(R.id.tvMinMax)
        val textView = view?.findViewById<TextView>(R.id.textView)
        val tvIcon = view?.findViewById<ImageView>(R.id.tvIcone)
        when (state) {
            is WeatherViewModel.State.ContentLoaded -> {
                tvData?.text = state.mainData.weatherByHour.first().dateTxt.substring(0, 10)
                tvSunny?.text = state.mainData.weatherByHour.first().weatherIcon.first().description
                tvTemper?.text =
                    state.mainData.weatherByHour.first().mainTemp.temp.toInt().toString()
                        .let { "$it°C" }
                tvMinMax?.text =
                    state.mainData.weatherByHour.first().mainTemp.let { "Min ${it.temp_min.toInt()}°/Max ${it.temp_max.toInt()}°" }
                textView?.text = state.mainData.citiName
                Picasso.get()
                    .load(ICON_URL_PART_ONE + state.mainData.weatherByHour.first().weatherIcon.first().icon + ICON_URL_PART_TWO)
                    .into(tvIcon)
                adapter.setList(state.mainData.weatherByHour)
            }
            WeatherViewModel.State.Error -> Log.d(TAG, "Fun render - does not work correctly")
            WeatherViewModel.State.LoadContent -> Log.d(TAG, "Fun render - content upload")
        }
    }

    private fun getAppBar() {
        appBarLayout.addOnOffsetChangedListener(object : OnOffsetChangedListener {
            var isShow = false
            var scrollRange = -1
            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.title =
                        "Title"
                    isShow = false
                } else if (!isShow) {
                    collapsingToolbarLayout.title = ""
                    isShow = true
                }
            }
        })
    }
}





