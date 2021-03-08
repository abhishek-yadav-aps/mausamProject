package com.abhitom.mausamproject.data.network

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class OpenWeatherAPIRetrofitClient {

    val openWeatherAPIService: OpenWeatherAPIService
    private val apiKey="22147f19dfcc656710c95cabb152527a"

    companion object {
        private var openWeatherAPIRetrofitClient: OpenWeatherAPIRetrofitClient? = null

        val instance: OpenWeatherAPIRetrofitClient
            get() {
                if (openWeatherAPIRetrofitClient == null) {
                    openWeatherAPIRetrofitClient = OpenWeatherAPIRetrofitClient()
                }
                return openWeatherAPIRetrofitClient as OpenWeatherAPIRetrofitClient
            }
    }

    init {

        val okHttpClient = OkHttpClient.Builder().addInterceptor { chain ->

            val originalUrl = chain.request().url

            val urlWithKey=originalUrl.newBuilder()
                    .addQueryParameter("appid", apiKey)
                    .build()

            val request = chain.request()
                    .newBuilder()
                    .url(urlWithKey)
                    .build()

            chain.proceed(request)

        }.build()

        val gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()

        val retrofitClient = Retrofit.Builder().client(okHttpClient)
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        openWeatherAPIService = retrofitClient.create(OpenWeatherAPIService::class.java)

    }
    
}

