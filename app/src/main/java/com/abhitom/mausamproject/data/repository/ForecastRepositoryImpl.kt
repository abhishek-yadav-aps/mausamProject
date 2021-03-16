package com.abhitom.mausamproject.data.repository

import androidx.lifecycle.LiveData
import com.abhitom.mausamproject.data.database.CurrentWeatherDao
import com.abhitom.mausamproject.data.database.entity.Current
import com.abhitom.mausamproject.data.network.WeatherNetworkDataSource
import com.abhitom.mausamproject.data.network.response.OneCallResponse
import com.abhitom.mausamproject.data.provider.LastLocation
import com.abhitom.mausamproject.data.provider.LastTimeDataFetched
import com.abhitom.mausamproject.data.provider.LocationProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ForecastRepositoryImpl(
        private val currentWeatherDao: CurrentWeatherDao,
        private val weatherNetworkDataSource: WeatherNetworkDataSource,
        private val lastTimeDataFetched: LastTimeDataFetched,
        private val lastLocation: LastLocation,
        private val locationProvider: LocationProvider
) : ForecastRepository {

    init {
        weatherNetworkDataSource.downloadedCurrentWeather.observeForever{newCurrentWeather ->
            persistFetchedCurrentWeather(newCurrentWeather)
        }
    }

    override suspend fun getCurrentWeather(units:String): LiveData<Current> {
        return withContext(Dispatchers.IO) {
            initWeatherData(units)
            return@withContext currentWeatherDao.getWeather()
        }
    }

    private suspend fun initWeatherData(units: String) {
        if (locationProvider.hasLocationChanged(lastLocation.getLastLocation())){
            fetchCurrentWeather(units)
        }
        else if (isFetchCurrentNeeded(lastTimeDataFetched.getCurrentLastTime())){
            fetchCurrentWeather(units)
        }
    }

    private suspend fun fetchCurrentWeather(units: String) {
        val location=locationProvider.getPrefLocation()
        weatherNetworkDataSource.fetchCurrentWeather(location.first,location.second,units)
        lastLocation.setLastLocation(location)
    }

    private fun isFetchCurrentNeeded(lastFetchTime: Long) : Boolean{
        return System.currentTimeMillis().minus(lastFetchTime)>1800000
    }

    private fun persistFetchedCurrentWeather(fetchedWeather: OneCallResponse){
        GlobalScope.launch(Dispatchers.IO) {
            lastTimeDataFetched.setCurrentLastTime(System.currentTimeMillis())
            currentWeatherDao.upsert(fetchedWeather.current!!)
        }
    }
}