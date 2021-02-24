package com.abhitom.mausamproject.internal

import androidx.room.TypeConverter
import com.abhitom.mausamproject.data.database.entity.WeatherItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


public class Converters {
    var gson = Gson()

    @TypeConverter
    fun stringToSomeObjectList(data: String?): MutableList<WeatherItem?>? {
        if (data == null) {
            return mutableListOf<WeatherItem?>()
        }
        val listType: Type = object : TypeToken<MutableList<WeatherItem?>?>() {}.type
        return gson.fromJson<MutableList<WeatherItem?>>(data, listType)
    }

    @TypeConverter
    fun someObjectListToString(someObjects: MutableList<WeatherItem?>?): String? {
        return gson.toJson(someObjects)
    }
}