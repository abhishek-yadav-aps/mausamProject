package com.abhitom.mausamproject.data.database

import android.content.Context
import androidx.room.*
import com.abhitom.mausamproject.data.database.entity.Current
import com.abhitom.mausamproject.data.database.entity.DailyItem
import com.abhitom.mausamproject.data.database.entity.ReverseGeoCodingApiResponse
import com.abhitom.mausamproject.internal.Converters

@Database(
    entities = [Current::class,DailyItem::class,ReverseGeoCodingApiResponse::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class ForecastDatabase : RoomDatabase(){

    abstract fun futureWeatherDao(): FutureWeatherDao
    abstract fun currentWeatherDao(): CurrentWeatherDao
    abstract fun currentLocationDao(): CurrentLocationDao

    companion object{
        @Volatile private var instance :ForecastDatabase?=null
        private  val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
            ForecastDatabase::class.java,
            "forecast.db").build()
    }
}