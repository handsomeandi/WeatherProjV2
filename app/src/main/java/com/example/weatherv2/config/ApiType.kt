package com.example.weatherv2.config


typealias Urls = String

object AppUrls {
    private const val apiID = "53c6e39cf3ee11a1d7549ffea83d6bd8"
    const val baseUrl: Urls = "https://api.openweathermap.org/data/2.5/"
    const val getWeatherUrl = "weather?appid=$apiID&units=metric&lang=ru"
}