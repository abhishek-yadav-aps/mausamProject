package com.abhitom.mausamproject.data.database.entity

import androidx.room.*
import com.abhitom.mausamproject.internal.Converters
import com.google.gson.annotations.SerializedName

const val CURRENT_WEATHER_ID = 0

@Entity(tableName = "current_weather")
data class Current(

        @field:SerializedName("sunrise")
        val sunrise: Long? = null,

        @field:SerializedName("temp")
        val temp: Double? = null,

        @field:SerializedName("visibility")
        val visibility: Int? = null,

        @field:SerializedName("uvi")
        val uvi: Double? = null,

        @field:SerializedName("pressure")
        val pressure: Int? = null,

        @field:SerializedName("clouds")
        val clouds: Int? = null,

        @field:SerializedName("feels_like")
        val feelsLike: Double? = null,

        @field:SerializedName("dt")
        val dt: Long? = null,

        @field:SerializedName("wind_deg")
        val windDeg: Int? = null,

        @field:SerializedName("sunset")
        val sunset: Long? = null,

        @TypeConverters(Converters::class)
        @field:SerializedName("weather")
        val weather: MutableList<WeatherItem?>? = null,

        @field:SerializedName("humidity")
        val humidity: Int? = null,

        @field:SerializedName("wind_speed")
        val windSpeed: Double? = null
){
    @PrimaryKey(autoGenerate = false)
    var id:Int = CURRENT_WEATHER_ID

    constructor() : this(0, 0.0, 0, 0.0, 0, 0, 0.0, 0, 0, 0 , mutableListOf(), 0, 0.0)
}


