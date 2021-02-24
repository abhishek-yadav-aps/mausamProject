package com.abhitom.mausamproject.ui.weather.current

import androidx.lifecycle.ViewModel
import com.abhitom.mausamproject.data.repository.ForecastRepository
import com.abhitom.mausamproject.internal.UnitSystem
import com.abhitom.mausamproject.internal.lazyDeferred

class CurrentWeatherViewModel(
        private val forecastRepository: ForecastRepository
) : ViewModel() {
    private val unitSystem= UnitSystem.METRIC

    val units: String
        get()  {
            if (unitSystem == UnitSystem.METRIC) {
                return "metric"
            }
            return "imperial"
        }
    val weather by lazyDeferred {
        forecastRepository.getCurrentWeather(units)
    }
}