package com.abhitom.mausamproject.data.provider

import android.content.Context
import android.content.SharedPreferences

class LastLocationImpl(context:Context) : LastLocation {
    private val appContext = context.applicationContext
    private  val preferences: SharedPreferences
        get() = appContext.getSharedPreferences("com.abhitom.mausamproject", Context.MODE_PRIVATE)
    override fun getLastLocation(): Pair<Double, Double> {
        val defaultLocation = Pair(28.7041.toString(),77.1025.toString())
        val lan=preferences.getString("last_location_lan",defaultLocation.first)!!.toDouble()
        val lon=preferences.getString("last_location_lon",defaultLocation.second)!!.toDouble()
        return Pair(lan,lon)
    }

    override fun setLastLocation(lastTime: Pair<Double, Double>) {
        preferences.edit().putString("last_location_lan",lastTime.first.toString()).apply()
        preferences.edit().putString("last_location_lon",lastTime.second.toString()).apply()
    }
}