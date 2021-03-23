package com.abhitom.mausamproject.data.repository

import androidx.lifecycle.LiveData
import com.abhitom.mausamproject.data.database.entity.Current
import com.abhitom.mausamproject.data.database.entity.DailyItem
import com.abhitom.mausamproject.data.database.entity.ReverseGeoCodingApiResponse

interface ForecastRepository {
    suspend fun getCurrentWeather(units:String): LiveData<out Current>

    suspend fun getFutureWeatherList(startDate:Long,units:String):LiveData<out List<DailyItem>>

    suspend fun getCurrentLocation(units:String):LiveData<out ReverseGeoCodingApiResponse>
}