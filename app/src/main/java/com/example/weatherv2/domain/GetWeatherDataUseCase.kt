package com.example.weatherv2.domain

import com.example.weatherv2.data.repository.WeatherRepository
import com.example.weatherv2.domain.model.TownWeather
import javax.inject.Inject

class GetWeatherDataUseCase @Inject constructor(private val repository: WeatherRepository) {

    suspend fun getWeatherData(townName: String): TownWeather = repository.getWeatherData(townName)

    suspend fun getCurrentLocationWeather(lat: String, lon: String): TownWeather =
        repository.getCurrentLocationWeather(lat, lon)

}