package com.example.weatherv2.domain.model

data class TownWeather(
    val town: Town,
    val weatherInfo: List<WeatherInfo>
)