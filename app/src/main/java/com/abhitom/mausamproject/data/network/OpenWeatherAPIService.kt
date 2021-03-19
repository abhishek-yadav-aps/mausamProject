package com.abhitom.mausamproject.data.network

import com.abhitom.mausamproject.data.database.entity.ReverseGeoCodingApiResponse
import com.abhitom.mausamproject.data.network.response.OneCallResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherAPIService {

    @GET("data/2.5/onecall")
    fun oneCallApi(@Query("lat") lat: Double, @Query("lon") lon: Double, @Query("units") units: String): Call<OneCallResponse>

    @GET("geo/1.0/reverse")
    fun reverseGeoCoding(@Query("lat") lat: Double, @Query("lon") lon: Double, @Query("limit") limit: Int): Call<List<ReverseGeoCodingApiResponse>>

}