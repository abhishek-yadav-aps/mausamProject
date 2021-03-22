package com.abhitom.mausamproject.ui.weather

import androidx.lifecycle.ViewModel
import com.abhitom.mausamproject.data.provider.UnitProvider
import com.abhitom.mausamproject.data.repository.ForecastRepository
import com.abhitom.mausamproject.internal.UnitSystem

abstract class WeatherViewModel(
        private val forecastRepository: ForecastRepository,
        unitProvider: UnitProvider) : ViewModel() {
    private val unitSystem= unitProvider.getUnitSystem()
    val isMetric:Boolean
    get() {
        if (unitSystem == UnitSystem.METRIC) {
            return true
        }
        return false
    }
    val units: String
    get()  {
        if (unitSystem == UnitSystem.METRIC) {
            return "metric"
        }
        return "imperial"
    }
}