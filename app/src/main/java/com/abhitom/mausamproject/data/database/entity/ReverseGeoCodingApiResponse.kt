package com.abhitom.mausamproject.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

const val CURRENT_LOCATION_ID = 0

@Entity(tableName = "current_location")
data class ReverseGeoCodingApiResponse(

    @field:SerializedName("country")
    val country: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("lon")
    val lon: Double? = null,

    @field:SerializedName("lat")
    val lat: Double? = null
){
    @PrimaryKey(autoGenerate = false)
    var id:Int = CURRENT_LOCATION_ID

    constructor() : this("", "", 0.0, 0.0)
}