package com.abhitom.mausamproject.data.response

import com.google.gson.annotations.SerializedName

data class OneCallResponse(

	@field:SerializedName("current")
	val current: Current? = null,

	@field:SerializedName("timezone")
	val timezone: String? = null,

	@field:SerializedName("timezone_offset")
	val timezoneOffset: Int? = null,

	@field:SerializedName("daily")
	val daily: List<DailyItem?>? = null,

	@field:SerializedName("lon")
	val lon: Double? = null,

	@field:SerializedName("hourly")
	val hourly: List<HourlyItem?>? = null,

	@field:SerializedName("minutely")
	val minutely: List<MinutelyItem?>? = null,

	@field:SerializedName("lat")
	val lat: Double? = null
)

data class DailyItem(

	@field:SerializedName("sunrise")
	val sunrise: Long? = null,

	@field:SerializedName("temp")
	val temp: Temp? = null,

	@field:SerializedName("uvi")
	val uvi: Double? = null,

	@field:SerializedName("pressure")
	val pressure: Int? = null,

	@field:SerializedName("clouds")
	val clouds: Int? = null,

	@field:SerializedName("feels_like")
	val feelsLike: FeelsLike? = null,

	@field:SerializedName("dt")
	val dt: Long? = null,

	@field:SerializedName("pop")
	val pop: Double? = null,

	@field:SerializedName("wind_deg")
	val windDeg: Int? = null,

	@field:SerializedName("dew_point")
	val dewPoint: Double? = null,

	@field:SerializedName("sunset")
	val sunset: Long? = null,

	@field:SerializedName("weather")
	val weather: List<WeatherItem?>? = null,

	@field:SerializedName("humidity")
	val humidity: Int? = null,

	@field:SerializedName("wind_speed")
	val windSpeed: Double? = null,

	@field:SerializedName("snow")
	val snow: Int? = null,

	@field:SerializedName("rain")
	val rain: Double? = null
)

data class MinutelyItem(

	@field:SerializedName("dt")
	val dt: Int? = null,

	@field:SerializedName("precipitation")
	val precipitation: Int? = null
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

data class WeatherItem(

	@field:SerializedName("icon")
	val icon: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("main")
	val main: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
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
	val dt: Int? = null,

	@field:SerializedName("wind_deg")
	val windDeg: Int? = null,

	@field:SerializedName("dew_point")
	val dewPoint: Double? = null,

	@field:SerializedName("sunset")
	val sunset: Long? = null,

	@field:SerializedName("weather")
	val weather: List<WeatherItem?>? = null,

	@field:SerializedName("humidity")
	val humidity: Int? = null,

	@field:SerializedName("wind_speed")
	val windSpeed: Double? = null
)

data class Rain(

	@field:SerializedName("1h")
	val jsonMember1h: Double? = null
)

data class HourlyItem(

	@field:SerializedName("rain")
	val rain: Rain? = null,

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

	@field:SerializedName("pop")
	val pop: Double? = null,

	@field:SerializedName("wind_deg")
	val windDeg: Int? = null,

	@field:SerializedName("dew_point")
	val dewPoint: Double? = null,

	@field:SerializedName("weather")
	val weather: List<WeatherItem?>? = null,

	@field:SerializedName("humidity")
	val humidity: Int? = null,

	@field:SerializedName("wind_speed")
	val windSpeed: Double? = null
)
