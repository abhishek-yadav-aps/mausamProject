package com.abhitom.mausamproject.data.provider

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.content.ContextCompat
import com.abhitom.mausamproject.internal.LocationPermissionNotGrantedException
import com.abhitom.mausamproject.internal.asDeferred
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.Deferred
import kotlin.math.abs

const val USE_DEVICE_LOCATION = "USE_DEVICE_LOCATION"

class LocationProviderImpl(private val fusedLocationProviderClient: FusedLocationProviderClient,
                           context: Context) : PreferenceProvider(context),LocationProvider {

    private val appContext=context.applicationContext

    override suspend fun hasLocationChanged(lastWeatherLocation: Pair<Double,Double>): Boolean {
        val deviceLocationChanged = try {
            hasDeviceLocationChanged(lastWeatherLocation)
        }catch (e:LocationPermissionNotGrantedException){
            false
        }
        return deviceLocationChanged
    }

    private suspend fun hasDeviceLocationChanged(lastWeatherLocation: Pair<Double, Double>): Boolean {
        if(!isUsingDeviceLocation())
            return false

        val deviceLocation = getLastDeviceLocation().await() ?:return false

        val comparisonThreshold=0.03
        return abs(deviceLocation.latitude - lastWeatherLocation.first) > comparisonThreshold &&
                abs(deviceLocation.longitude - lastWeatherLocation.second) > comparisonThreshold
    }

    @SuppressLint("MissingPermission")
    private fun getLastDeviceLocation(): Deferred<Location?> {
        return if (hasLocationPermission())
            fusedLocationProviderClient.lastLocation.asDeferred()
        else
            throw LocationPermissionNotGrantedException()
    }

    private fun isUsingDeviceLocation(): Boolean {
        return preferences.getBoolean(USE_DEVICE_LOCATION,true)
    }

    override suspend fun getPrefLocation(): Pair<Double,Double> {
        val defaultLocation = Pair(28.7041,77.1025)
        if (isUsingDeviceLocation()){
            try {
                val deviceLocation=getLastDeviceLocation().await()
                        ?: return defaultLocation
                return Pair(deviceLocation.latitude,deviceLocation.longitude)
            }catch (e:LocationPermissionNotGrantedException){
                return defaultLocation
            }
        }
        return defaultLocation
    }

    private fun hasLocationPermission():Boolean{
        return ContextCompat.checkSelfPermission(appContext,Manifest.permission.ACCESS_COARSE_LOCATION)==PackageManager.PERMISSION_GRANTED
    }
} 