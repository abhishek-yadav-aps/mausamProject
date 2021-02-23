package com.abhitom.mausamproject.data.network.response

import com.abhitom.mausamproject.data.database.entity.Current
import com.abhitom.mausamproject.data.database.entity.DailyItem
import com.abhitom.mausamproject.data.database.entity.HourlyItem
import com.google.gson.annotations.SerializedName

data class OneCallResponse(

	@field:SerializedName("current")
	val current: Current? = null,

	@field:SerializedName("timezone")
	val timezone: String? = null,

	@field:SerializedName("daily")
	val daily: List<DailyItem?>? = null,

	@field:SerializedName("lon")
	val lon: Double? = null,

	@field:SerializedName("hourly")
	val hourly: List<HourlyItem?>? = null,

	@field:SerializedName("lat")
	val lat: Double? = null
)
