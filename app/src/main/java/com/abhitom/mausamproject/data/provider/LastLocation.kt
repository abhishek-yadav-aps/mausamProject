package com.abhitom.mausamproject.data.provider

interface LastLocation {
    fun getLastLocation():Pair<Double,Double>
    fun setLastLocation(lastTime:Pair<Double,Double>)
}
