package com.abhitom.mausamproject

import android.app.Application
import com.abhitom.mausamproject.data.database.ForecastDatabase
import com.abhitom.mausamproject.data.network.WeatherNetworkDataSource
import com.abhitom.mausamproject.data.network.WeatherNetworkDataSourceImpl
import com.abhitom.mausamproject.data.repository.ForecastRepository
import com.abhitom.mausamproject.data.repository.ForecastRepositoryImpl
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class ForecastApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@ForecastApplication))

        bind() from singleton { ForecastDatabase(instance()) }
        bind() from singleton { instance<ForecastDatabase>().currentWeatherDao() }
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance())}
        bind<ForecastRepository>() with singleton { ForecastRepositoryImpl(instance(),instance()) }
    }
}