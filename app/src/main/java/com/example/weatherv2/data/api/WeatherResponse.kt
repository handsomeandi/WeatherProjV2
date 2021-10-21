package com.example.weatherv2.data.api

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("main") val main: MainObject? = null,
    @SerializedName("weather") val weather: List<WeatherObject>? = null,
    @SerializedName("wind") val wind: WindObject? = null,
    @SerializedName("sys") var sys: SunObject,
    @SerializedName("name") var townName: String? = null,
    @SerializedName("id") var townID: String? = null
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