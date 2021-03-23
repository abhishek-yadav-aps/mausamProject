package com.abhitom.mausamproject.data.network

import androidx.lifecycle.LiveData
import com.abhitom.mausamproject.data.database.entity.ReverseGeoCodingApiResponse
import com.abhitom.mausamproject.data.network.response.OneCallResponse

interface WeatherNetworkDataSource {
    val downloadedWeather: LiveData<OneCallResponse>
    val downloadedLocation: LiveData<ReverseGeoCodingApiResponse>

    fun fetchWeather(
            lat:Double,
            lon:Double,
            units:String
    )

    fun fetchLocation(
            lat:Double,
            lon: Double
    )
}