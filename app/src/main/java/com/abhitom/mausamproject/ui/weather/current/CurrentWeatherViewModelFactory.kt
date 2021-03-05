package com.abhitom.mausamproject.ui.weather.current

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.abhitom.mausamproject.data.provider.UnitProvider
import com.abhitom.mausamproject.data.repository.ForecastRepository

class CurrentWeatherViewModelFactory(
        private val forecastRepository: ForecastRepository,
        private val unitProvider: UnitProvider
): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CurrentWeatherViewModel(forecastRepository,unitProvider) as T
    }
}