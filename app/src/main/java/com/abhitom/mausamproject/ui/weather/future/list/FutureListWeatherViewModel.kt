package com.abhitom.mausamproject.ui.weather.future.list

import com.abhitom.mausamproject.data.provider.UnitProvider
import com.abhitom.mausamproject.data.repository.ForecastRepository
import com.abhitom.mausamproject.internal.lazyDeferred
import com.abhitom.mausamproject.ui.weather.WeatherViewModel

class FutureListWeatherViewModel(
        private val forecastRepository: ForecastRepository,
        unitProvider: UnitProvider) : WeatherViewModel(forecastRepository, unitProvider) {
    val weather by lazyDeferred {
        forecastRepository.getFutureWeatherList(System.currentTimeMillis()/1000,super.units)
    }
    val location by lazyDeferred {
        forecastRepository.getCurrentLocation(super.units)
    }
}