package com.abhitom.mausamproject.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abhitom.mausamproject.data.database.entity.CURRENT_WEATHER_ID
import com.abhitom.mausamproject.data.database.entity.CURRENT_WEATHER_ID_IMPERIAL
import com.abhitom.mausamproject.data.database.entity.Current

@Dao
interface CurrentWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherEntry:Current)

    @Query("select * from current_weather where id = $CURRENT_WEATHER_ID")
    fun getWeather(): LiveData<Current>

    @Query("select * from current_weather where id = $CURRENT_WEATHER_ID_IMPERIAL")
    fun getWeatherImperial(): LiveData<Current>
}