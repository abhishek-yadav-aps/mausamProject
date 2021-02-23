package com.abhitom.mausamproject.data.network

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abhitom.mausamproject.data.network.response.OneCallResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherNetworkDataSourceImpl(context: Context) : weatherNetworkDataSource {

    private val appContext=context.applicationContext
    private var _downloadedCurrentWeather = MutableLiveData<OneCallResponse>()
    override val downloadedCurrentWeather: LiveData<OneCallResponse>
        get() = _downloadedCurrentWeather

    override fun fetchCurrentWeather(lat: Double, lon: Double, units: String) {
        Toast.makeText(appContext,"RUN ",Toast.LENGTH_LONG).show()
        OpenWeatherAPIRetrofitClient.instance.openWeatherAPIService.oneCallApi(lat,lon , units)
                .enqueue(object : Callback<OneCallResponse> {
                    override fun onResponse(
                            call: Call<OneCallResponse>,
                            response: Response<OneCallResponse>
                    ) {
                        if (response.isSuccessful) {
                            _downloadedCurrentWeather.postValue(response.body())
                            Toast.makeText(appContext,"SUCCESS",Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(appContext,response.errorBody().toString(),Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: Call<OneCallResponse>, t: Throwable) {
                        Toast.makeText(appContext,"NO INTERNET",Toast.LENGTH_LONG).show()
                    }
                })
    }
}