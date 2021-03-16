package com.abhitom.mausamproject.data.provider

interface LocationProvider {
    suspend fun hasLocationChanged(lastWeatherLocation:Pair<Double,Double>):Boolean

    suspend fun getPrefLocation():Pair<Double,Double>
}