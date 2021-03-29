package com.abhitom.mausamproject.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abhitom.mausamproject.data.database.entity.HourlyItem

@Dao
interface HourlyWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(hourlyWeatherEntry: List<HourlyItem>)

    @Query("select * from hourly_weather")
    fun getHourlyWeather(): LiveData<List<HourlyItem>>

    @Query("delete from hourly_weather")
    fun deleteOldEntries()
}