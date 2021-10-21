package com.example.weatherv2.data.api

import com.example.weatherv2.config.AppUrls
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET(AppUrls.getWeatherUrl)
    suspend fun getWeatherData(@Query("q") townName: String): WeatherResponse

    @GET(AppUrls.getWeatherUrl)
    suspend fun getCurrentLocationWeather(@Query("lat") lat: String, @Query("lon") lon: String): WeatherResponse
}