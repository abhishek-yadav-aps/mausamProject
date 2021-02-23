package com.abhitom.mausamproject.ui.weather.current

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.abhitom.mausamproject.data.network.OpenWeatherAPIRetrofitClient
import com.abhitom.mausamproject.data.network.WeatherNetworkDataSourceImpl
import com.abhitom.mausamproject.data.network.response.OneCallResponse
import com.abhitom.mausamproject.databinding.CurrentWeatherFragmentBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CurrentWeatherFragment : Fragment() {

    lateinit var binding: CurrentWeatherFragmentBinding

    companion object {
        fun newInstance() = CurrentWeatherFragment()
    }

    private lateinit var viewModel: CurrentWeatherViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = CurrentWeatherFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CurrentWeatherViewModel::class.java)

        val weatherNetworkDataSource = WeatherNetworkDataSourceImpl(this.requireContext())

        weatherNetworkDataSource.downloadedCurrentWeather.observe(this.viewLifecycleOwner, Observer {
            binding.tvCurrentWeatherFragment.text = it.toString()
        })

        weatherNetworkDataSource.fetchCurrentWeather(33.441792,94.037689,"metric")
    }

}