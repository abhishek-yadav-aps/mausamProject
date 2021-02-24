package com.abhitom.mausamproject.data.repository

import androidx.lifecycle.LiveData
import com.abhitom.mausamproject.data.database.entity.Current

interface ForecastRepository {
    suspend fun getCurrentWeather(units:String): LiveData<Current>
}