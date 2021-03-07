package com.abhitom.mausamproject.data.provider

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

class LastTimeDataFetchedImpl(context: Context) : LastTimeDataFetched {
    private val appContext = context.applicationContext
    private  val preferences: SharedPreferences
        get() = appContext.getSharedPreferences("com.abhitom.mausamproject",Context.MODE_PRIVATE)
    override fun getCurrentLastTime(): Long {
        return preferences.getLong("current_last_time",System.currentTimeMillis())
    }

    override fun setCurrentLastTime(lastTime: Long) {
        preferences.edit().putLong("current_last_time",lastTime).apply()
    }
}