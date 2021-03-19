package com.abhitom.mausamproject.data.database.entity

import androidx.room.*
import com.abhitom.mausamproject.internal.Converters
import com.google.gson.annotations.SerializedName

@Entity(tableName = "future_weather", indices = [Index(value = ["dt"],unique = true)])
data class DailyItem(

    @PrimaryKey(autoGenerate = true)
    val id:Int?=null,

    @field:SerializedName("sunrise")
    val sunrise: Long? = null,

    @Embedded(prefix = "temp_")
    @field:SerializedName("temp")
    val temp: Temp? = null,

    @field:SerializedName("uvi")
    val uvi: Double? = null,

    @field:SerializedName("pressure")
    val pressure: Int? = null,

    @field:SerializedName("clouds")
    val clouds: Int? = null,

    @Embedded(prefix = "feels_like_")
    @field:SerializedName("feels_like")
    val feelsLike: FeelsLike? = null,

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
    val windSpeed: Double? = null,
)

data class Temp(

    @field:SerializedName("min")
    val min: Double? = null,

    @field:SerializedName("max")
    val max: Double? = null,

    @field:SerializedName("eve")
    val eve: Double? = null,

    @field:SerializedName("night")
    val night: Double? = null,

    @field:SerializedName("day")
    val day: Double? = null,

    @field:SerializedName("morn")
    val morn: Double? = null
)

data class FeelsLike(

    @field:SerializedName("eve")
    val eve: Double? = null,

    @field:SerializedName("night")
    val night: Double? = null,

    @field:SerializedName("day")
    val day: Double? = null,

    @field:SerializedName("morn")
    val morn: Double? = null
)