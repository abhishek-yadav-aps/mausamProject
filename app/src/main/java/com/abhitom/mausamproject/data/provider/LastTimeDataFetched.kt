package com.abhitom.mausamproject.data.provider

interface LastTimeDataFetched {
    fun getCurrentLastTime():Long
    fun setCurrentLastTime(lastTime:Long)
}