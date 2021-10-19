package com.example.weatherv2.data

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("main") private val main: MainObject? = null,
    @SerializedName("weather") private val weather: List<WeatherObject>? = null,
    @SerializedName("wind") private val wind: WindObject? = null,
    @SerializedName("sys") private var sys: SunObject,
    @SerializedName("name") var townName: String? = null
)

data class MainObject(
    @SerializedName("temp") var temp: String? = null,
    @SerializedName("humidity") var humidity: String? = null
)

data class WeatherObject(
    @SerializedName("description") var description: String? = null
)

data class WindObject(
    @SerializedName("speed") var speed: String? = null
)

data class SunObject(
    @SerializedName("sunrise") var sunrise: Long = 0,
    @SerializedName("sunset") var sunset: Long = 0
)