package com.abhitom.mausamproject.data.repository

import androidx.lifecycle.LiveData
import com.abhitom.mausamproject.data.database.CurrentWeatherDao
import com.abhitom.mausamproject.data.database.entity.Current
import com.abhitom.mausamproject.data.network.WeatherNetworkDataSource
import com.abhitom.mausamproject.data.network.response.OneCallResponse
import com.abhitom.mausamproject.data.provider.LastTimeDataFetched
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ForecastRepositoryImpl(
        private val currentWeatherDao: CurrentWeatherDao,
        private val weatherNetworkDataSource: WeatherNetworkDataSource,
        private val lastTimeDataFetched: LastTimeDataFetched
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
        if (isFetchCurrentNeeded(lastTimeDataFetched.getCurrentLastTime())){
            fetchCurrentWeather(units)
        }
    }

    private suspend fun fetchCurrentWeather(units: String) {
        weatherNetworkDataSource.fetchCurrentWeather(28.7041,77.1025,units)
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