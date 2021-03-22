package com.abhitom.mausamproject.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abhitom.mausamproject.data.database.entity.DailyItem

@Dao
interface FutureWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(futureWeatherEntry: List<DailyItem>)

    @Query("select * from future_weather where dt >= :startDate")
    fun getFutureWeather(startDate:Long): LiveData<List<DailyItem>>

    @Query("delete from future_weather where dt < :firstDateToKeep")
    fun deleteOldEntries(firstDateToKeep: Long)
}