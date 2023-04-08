package com.example.myapplicationart.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import com.example.myapplicationart.MyApplication
import com.example.myapplicationart.R
import com.example.myapplicationart.data.network.ApiService
import com.example.myapplicationart.databinding.FragmentWeatherBinding
import com.example.myapplicationart.di.ViewModelFactory
import com.example.myapplicationart.domain.TAG
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.squareup.picasso.Picasso
import javax.inject.Inject

class FragmentWeather : Fragment(R.layout.fragment_weather) {

    @Inject
    lateinit var adapter: WeatherAdapter
    @Inject
    lateinit var dialogFragment: WeatherDialog
    @Inject
    lateinit var api: ApiService
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val model: WeatherViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[WeatherViewModel::class.java]
    }

    private var _binding: FragmentWeatherBinding? = null
    private val bG get() = _binding!!

    override fun onAttach(context: Context) {
        DaggerPresentationComponent
            .factory()
            .create(MyApplication.appComponent)
            .inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return bG.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bG.toolbar.inflateMenu(R.menu.fragment_weather)
        getAppBar()
        model.getDataWeather("Tambov")
        bG.tvRv.adapter = adapter
        model.myWeatherList.observe(viewLifecycleOwner, ::render)

        bG.buttonSearch2.setOnClickListener {
            dialogFragment.show(parentFragmentManager, "MyFragment")
        }

        bG.buttonSearch.setOnClickListener {
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
        when (state) {
            is WeatherViewModel.State.ContentLoaded -> {
                bG.tvData.text = state.dataText
                bG.tvSunny.text = state.weatherIconDescriptionToday
                bG.tvTemper.text = state.tempToday
                bG.tvMinMax.text = state.maxAndMinTempToday
                bG.textView.text = state.cityName
                Picasso.get()
                    .load(state.iconUrl)
                    .into(bG.tvIcone)
                adapter.setList(state.weatherByHour)
            }
            WeatherViewModel.State.Error -> Log.d(TAG, "Fun render - does not work correctly")
            WeatherViewModel.State.LoadContent -> Log.d(TAG, "Fun render - content upload")
        }
    }

    private fun getAppBar() {
        bG.appBarLayout.addOnOffsetChangedListener(object : OnOffsetChangedListener {
            var isShow = false
            var scrollRange = -1
            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    bG.collapsingToolbar.title =
                        "Title"
                    isShow = false
                } else if (!isShow) {
                    bG.collapsingToolbar.title = ""
                    isShow = true
                }
            }
        })
    }
    companion object {
        fun newInstance() = FragmentWeather()
    }
}





