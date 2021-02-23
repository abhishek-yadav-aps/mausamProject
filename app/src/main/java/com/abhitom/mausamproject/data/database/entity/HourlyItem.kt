package com.abhitom.mausamproject.data.database.entity

import com.google.gson.annotations.SerializedName

data class HourlyItem(

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
    val dt: Int? = null,

    @field:SerializedName("wind_deg")
    val windDeg: Int? = null,

    @field:SerializedName("weather")
    val weather: List<WeatherItem?>? = null,

    @field:SerializedName("humidity")
    val humidity: Int? = null,

    @field:SerializedName("wind_speed")
    val windSpeed: Double? = null
)

