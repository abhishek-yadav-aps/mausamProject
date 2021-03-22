package com.abhitom.mausamproject.data.repository

import androidx.lifecycle.LiveData
import com.abhitom.mausamproject.data.database.entity.Current
import com.abhitom.mausamproject.data.database.entity.DailyItem

interface ForecastRepository {
    suspend fun getCurrentWeather(units:String): LiveData<out Current>

    suspend fun getFutureWeatherList(startDate:Long,units:String):LiveData<out List<DailyItem>>
}