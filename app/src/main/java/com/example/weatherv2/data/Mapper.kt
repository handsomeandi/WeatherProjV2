package com.example.weatherv2.data

import com.example.weatherv2.TownItems
import com.example.weatherv2.data.api.WeatherResponse
import com.example.weatherv2.domain.model.Town
import com.example.weatherv2.domain.model.TownWeather
import com.example.weatherv2.domain.model.WeatherInfo
import java.text.SimpleDateFormat
import java.util.*

object Mapper {
    fun WeatherResponse.toTownWeather() = TownWeather(toTown(), toWeatherData())

    fun WeatherResponse.toTown() = Town(id = townID ?: "-1", name = townName ?: "")

    fun WeatherResponse.toWeatherData(): List<WeatherInfo> {
        return listOf(
            WeatherInfo("Current temp", main?.temp ?: "-"),
            WeatherInfo("Weather conditions", weather?.get(0)?.description ?: "-"),
            WeatherInfo("Wind speed", wind?.speed ?: "-"),
            WeatherInfo("Humidity", main?.humidity ?: "-"),
            WeatherInfo("Sunrise time", getDate(sys.sunrise) ?: "-"),
            WeatherInfo("Sunset time", getDate(sys.sunset) ?: "-"),
        )
    }

    fun TownItems.toTown() = Town(id, name)

    private fun getDate(seconds: Long, dateFormat: String? = "HH:mm:ss"): String? {
        SimpleDateFormat(dateFormat, Locale("ru")).let {
            val calendar: Calendar = Calendar.getInstance().apply {
                timeInMillis = seconds * 1000
            }
            return it.format(calendar.time)
        }
    }
}