package com.example.myapplicationart.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplicationart.DialogManager
import com.example.myapplicationart.R
import com.example.myapplicationart.dagger2.AppComponent
import com.example.myapplicationart.dagger2.DaggerAppComponent
import com.example.myapplicationart.dagger2.ViewModelFactory
import com.example.myapplicationart.retrofit2RXjava.ApiServiceRX
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.squareup.picasso.Picasso
import javax.inject.Inject

class FragmentWeather : Fragment(R.layout.fragment_weather) {

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: WeatherAdapter
    lateinit var collapsingToolbarLayout: CollapsingToolbarLayout
    lateinit var toolBar: Toolbar
    lateinit var appBarLayout: AppBarLayout
    lateinit var collapsingMenu: Menu
    lateinit var dialogFragment: WeatherDialog
    lateinit var appComponent: AppComponent
    lateinit var sampleViewModel: WeatherViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var api: ApiServiceRX
    //lateinit var component: NetworkComponent
    
    private val model: WeatherViewModel by lazy {
        ViewModelProvider(this,viewModelFactory)[WeatherViewModel::class.java]
    }
    // Равносильтно ? model = ViewModelProvider(this).get(model::class.java)


    override fun onAttach(context: Context) {

        appComponent = DaggerAppComponent.create()
        appComponent.inject(this)

        super.onAttach(context)
       //sampleViewModel = ViewModelProvider(this).get(sampleViewModel::class.java)
        //val viewModelFactory = ViewModelFactory(WeatherViewModel())
        // val viewModel = ViewModelProvider(this,viewModelFactory)[WeatherViewModel::class.java]
    }

    companion object {
        fun newInstance() = FragmentWeather()
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collapsingToolbarLayout = view.findViewById(R.id.collapsing_toolbar)
        toolBar = view.findViewById(R.id.toolbar)
       // setSupportActionBar(findViewById<Toolbar>(R.id.toolbar))
        appBarLayout = view.findViewById(R.id.appBarLayout)
        getAppBar()
        toolBar.inflateMenu(R.menu.fragment_weather)
//        setHasOptionsMenu(true)
        recyclerView = view.findViewById(R.id.tvRv)
        model.getDataWeather("Tambov")
        model.getDataWeatherRX()
        adapter = WeatherAdapter()
        recyclerView.adapter = adapter
        model.myWeatherList.observe(viewLifecycleOwner, ::render)

        val buttonSearch = view.findViewById<ImageButton>(R.id.buttonSearch)
        val buttonSearch2 = view.findViewById<ImageButton>(R.id.buttonSearch2)

        buttonSearch2.setOnClickListener {
//            val dialogFragment = WeatherDialog()
            dialogFragment = appComponent.getWeatherDialog()
            dialogFragment.show(parentFragmentManager, "My Fragment")
        }

        buttonSearch.setOnClickListener {
            DialogManager.showAlertDialog(
                requireContext(), object : DialogManager.Listener {
                    override fun onClick(name: String?) {
                        name?.let {
                                it -> model.getDataWeather(it)
                        }
                    }
                })
        }
    }

    override fun onResume() {
        super.onResume()
        setFragmentResultListener("key") { key, bundle ->
            val result = bundle.getString("bundleKey")
            Log.d("Loge1",result.toString())
            model.getDataWeather(result.toString())
        }
    }

    private fun render(state: WeatherViewModel.State) {
        var tvData = view?.findViewById<TextView>(R.id.tvData)
        var tvSunny = view?.findViewById<TextView>(R.id.tvSunny)
        var tvTemper = view?.findViewById<TextView>(R.id.tvTemper)
        var tvMinMax = view?.findViewById<TextView>(R.id.tvMinMax)
        var textView = view?.findViewById<TextView>(R.id.textView)
        var tvIcone = view?.findViewById<ImageView>(R.id.tvIcone)
        when (state) {
            is WeatherViewModel.State.ContentLoaded -> {
                tvData?.text = state.mainData.weatherByHour.first().dateTxt.substring(0, 10)
                tvSunny?.text = state.mainData.weatherByHour.first().weatherIcon.first().description
                tvTemper?.text =
                    state.mainData.weatherByHour.first().mainTemp.temp.toInt().toString()
                        .let { it + "°C" }
                tvMinMax?.text =
                    state.mainData.weatherByHour.first().mainTemp.let { "Min ${it.temp_min.toInt()}°/Max ${it.temp_max.toInt()}°" }
                textView?.text = state.mainData.citiName
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





