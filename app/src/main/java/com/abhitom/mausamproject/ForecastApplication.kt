package com.abhitom.mausamproject

import android.app.Application
import android.content.Context
import androidx.preference.PreferenceManager
import com.abhitom.mausamproject.data.database.ForecastDatabase
import com.abhitom.mausamproject.data.network.WeatherNetworkDataSource
import com.abhitom.mausamproject.data.network.WeatherNetworkDataSourceImpl
import com.abhitom.mausamproject.data.provider.*
import com.abhitom.mausamproject.data.repository.ForecastRepository
import com.abhitom.mausamproject.data.repository.ForecastRepositoryImpl
import com.abhitom.mausamproject.internal.ToastMaker
import com.abhitom.mausamproject.internal.ToastMakerImpl
import com.abhitom.mausamproject.ui.weather.current.CurrentWeatherViewModelFactory
import com.abhitom.mausamproject.ui.weather.future.list.FutureListWeatherViewModelFactory
import com.abhitom.mausamproject.ui.weather.future.list.FutureWeatherListCurlyItemDecorator
import com.google.android.gms.location.LocationServices
import com.jakewharton.threetenabp.AndroidThreeTen
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class ForecastApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@ForecastApplication))

        bind() from singleton { ForecastDatabase(instance()) }
        bind() from singleton { instance<ForecastDatabase>().currentWeatherDao() }
        bind() from singleton { instance<ForecastDatabase>().futureWeatherDao() }
        bind() from singleton { instance<ForecastDatabase>().currentLocationDao() }
        bind<ToastMaker>() with singleton { ToastMakerImpl(instance()) }
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance(),instance())}
        bind() from provider { LocationServices.getFusedLocationProviderClient(instance<Context>()) }
        bind<LocationProvider>() with singleton{LocationProviderImpl(instance(),instance())}
        bind<LastLocation>() with singleton { LastLocationImpl(instance()) }
        bind<LastTimeDataFetched>() with singleton { LastTimeDataFetchedImpl(instance()) }
        bind<ForecastRepository>() with singleton { ForecastRepositoryImpl(instance(),instance(),instance(),instance(),instance(),instance(),instance()) }
        bind<UnitProvider>() with singleton { UnitProviderImpl(instance()) }
        bind() from provider{ CurrentWeatherViewModelFactory(instance(),instance()) }
        bind() from provider{ FutureListWeatherViewModelFactory(instance(),instance()) }
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        PreferenceManager.setDefaultValues(this,R.xml.preferences,false)
    }
}