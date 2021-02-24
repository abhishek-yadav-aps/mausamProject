package com.abhitom.mausamproject.data.network

import androidx.lifecycle.LiveData
import com.abhitom.mausamproject.data.network.response.OneCallResponse

interface WeatherNetworkDataSource {
    val downloadedCurrentWeather: LiveData<OneCallResponse>

    fun fetchCurrentWeather(
            lat:Double,
            lon:Double,
            units:String
    )
}