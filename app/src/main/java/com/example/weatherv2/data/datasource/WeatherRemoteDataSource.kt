package com.example.weatherv2.data.datasource

import com.example.weatherv2.data.api.WeatherApi
import com.example.weatherv2.data.api.WeatherResponse
import javax.inject.Inject

class WeatherRemoteDataSource @Inject constructor(private val api: WeatherApi) {

    suspend fun getWeatherData(townName: String): WeatherResponse {
        return api.getWeatherData(townName)
    }

    suspend fun getCurrentLocationWeather(lat: String, lon: String): WeatherResponse {
        return api.getCurrentLocationWeather(lat, lon)
    }
}