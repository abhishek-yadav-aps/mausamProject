package com.abhitom.mausamproject.data.repository

import androidx.lifecycle.LiveData
import com.abhitom.mausamproject.data.database.CurrentWeatherDao
import com.abhitom.mausamproject.data.database.entity.Current
import com.abhitom.mausamproject.data.network.WeatherNetworkDataSource
import com.abhitom.mausamproject.data.network.response.OneCallResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime

class ForecastRepositoryImpl(
        private val currentWeatherDao: CurrentWeatherDao,
        private val weatherNetworkDataSource: WeatherNetworkDataSource
) : ForecastRepository {

    init {
        weatherNetworkDataSource.downloadedCurrentWeather.observeForever{newCurrentWeather ->
            persistFetchedCurrentWeather(newCurrentWeather)
        }
    }

    override suspend fun getCurrentWeather(): LiveData<Current> {
        return withContext(Dispatchers.IO) {
            initWeatherData()
            return@withContext currentWeatherDao.getWeather()
        }
    }

    private suspend fun initWeatherData() {
        if (isFetchCurrentNeeded(ZonedDateTime.now().minusHours(1))){
            fetchCurrentWeather()
        }
    }

    private suspend fun fetchCurrentWeather(){
        weatherNetworkDataSource.fetchCurrentWeather(33.441792,94.037689,"metric")
    }

    private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime) : Boolean{
        val thirtyMinuteAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinuteAgo)
    }

    private fun persistFetchedCurrentWeather(fetchedWeather: OneCallResponse){
        GlobalScope.launch(Dispatchers.IO) {
            currentWeatherDao.upsert(fetchedWeather.current!!)
        }
    }
}