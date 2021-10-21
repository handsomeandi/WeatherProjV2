package com.example.weatherv2.ui.weather_screen

sealed class WeatherIntent {
    class RequestWeather(val townName: String?): WeatherIntent()
}