package com.abhitom.mausamproject.ui.weather.current

import com.abhitom.mausamproject.data.provider.UnitProvider
import com.abhitom.mausamproject.data.repository.ForecastRepository
import com.abhitom.mausamproject.internal.lazyDeferred
import com.abhitom.mausamproject.ui.weather.WeatherViewModel

class CurrentWeatherViewModel(
        private val forecastRepository: ForecastRepository,
        unitProvider: UnitProvider
) : WeatherViewModel(forecastRepository, unitProvider) {
    val weather by lazyDeferred {
        forecastRepository.getCurrentWeather(super.units)
    }
}