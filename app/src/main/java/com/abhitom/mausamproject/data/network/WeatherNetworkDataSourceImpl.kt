package com.abhitom.mausamproject.data.network

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abhitom.mausamproject.data.database.entity.CURRENT_WEATHER_ID
import com.abhitom.mausamproject.data.database.entity.CURRENT_WEATHER_ID_IMPERIAL
import com.abhitom.mausamproject.data.database.entity.ReverseGeoCodingApiResponse
import com.abhitom.mausamproject.data.network.response.OneCallResponse
import com.abhitom.mausamproject.internal.ToastMaker
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherNetworkDataSourceImpl(context: Context,
    private val toastMaker: ToastMaker
    ) : WeatherNetworkDataSource {

    private val appContext=context.applicationContext
    private var _downloadedWeather = MutableLiveData<OneCallResponse>()
    override val downloadedWeather: LiveData<OneCallResponse>
        get() = _downloadedWeather
    private var _downloadedWeatherImperial = MutableLiveData<OneCallResponse>()
    override val downloadedWeatherImperial: LiveData<OneCallResponse>
        get() = _downloadedWeatherImperial

    private var _downloadedLocation = MutableLiveData<ReverseGeoCodingApiResponse>()
    override val downloadedLocation: LiveData<ReverseGeoCodingApiResponse>
        get() = _downloadedLocation

    override fun fetchWeather(lat: Double, lon: Double, units: String) {

        OpenWeatherAPIRetrofitClient.instance.openWeatherAPIService.oneCallApi(lat,lon , "metric")
                .enqueue(object : Callback<OneCallResponse> {
                    override fun onResponse(
                            call: Call<OneCallResponse>,
                            response: Response<OneCallResponse>
                    ) {
                        if (response.isSuccessful) {
                            val data=response.body()
                            data?.current?.id = CURRENT_WEATHER_ID
                            _downloadedWeather.postValue(data!!)
                        } else {
                            //toastMaker.toastMaker("ERROR CODE - ${response.code()}")
                        }
                    }

                    override fun onFailure(call: Call<OneCallResponse>, t: Throwable) {
                        //toastMaker.toastMaker("NO INTERNET")
                    }
                })

        OpenWeatherAPIRetrofitClient.instance.openWeatherAPIService.oneCallApi(lat,lon , "imperial")
                .enqueue(object : Callback<OneCallResponse> {
                    override fun onResponse(
                            call: Call<OneCallResponse>,
                            response: Response<OneCallResponse>
                    ) {
                        if (response.isSuccessful) {
                            val data=response.body()
                            data?.current?.id = CURRENT_WEATHER_ID_IMPERIAL
                            _downloadedWeatherImperial.postValue(data!!)
                        } else {
                            //toastMaker.toastMaker("ERROR CODE - ${response.code()}")
                        }
                    }

                    override fun onFailure(call: Call<OneCallResponse>, t: Throwable) {
                        //toastMaker.toastMaker("NO INTERNET")
                    }
                })


    }

    override fun fetchLocation(lat: Double, lon: Double) {
        OpenWeatherAPIRetrofitClient.instance.openWeatherAPIService.reverseGeoCoding(lat,lon,1)
                .enqueue(object : Callback<List<ReverseGeoCodingApiResponse>> {
                    override fun onResponse(
                            call: Call<List<ReverseGeoCodingApiResponse>>,
                            response: Response<List<ReverseGeoCodingApiResponse>>
                    ) {
                        if (response.isSuccessful) {
                            if (!response.body().isNullOrEmpty())
                                _downloadedLocation.postValue(response.body()?.get(0))

                        } else {
                            //toastMaker.toastMaker("ERROR CODE - ${response.code()}")
                        }
                    }

                    override fun onFailure(call: Call<List<ReverseGeoCodingApiResponse>>, t: Throwable) {
                        //toastMaker.toastMaker("NO INTERNET")
                    }
                })
    }
}