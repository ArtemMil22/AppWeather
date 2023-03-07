package com.example.myapplicationart.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplicationart.R

class HoursFragment : Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var adapter:WeatherAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val viewModel = ViewModelProvider(this)
            .get(WeatherViewModel::class.java)

        val view = inflater.inflate(
            R.layout.fragment_weather, container, false
        )
//        val layoutManagers =GridLayoutManager(context,2)
//        recyclerView.layoutManager = layoutManagers

//        setFragmentResultListener("key" ){ key,bundle ->
//            val result = bundle.getString("bundleKey")
//
//        }
//        recyclerView = view.tvRv
//        //viewModel.getDataWeather("Tambov")
//        adapter = WeatherAdapter()
//        recyclerView.adapter = adapter
//        viewModel.myWeatherList.observe(viewLifecycleOwner){
//            adapter.setList()
//        }
        return view
    }
    companion object {
        fun newInstance() = HoursFragment()
    }
}
