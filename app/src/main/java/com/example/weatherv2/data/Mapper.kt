package com.example.weatherv2.data

import com.example.weatherv2.AppExt.getString
import com.example.weatherv2.HiltApp
import com.example.weatherv2.R
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
            WeatherInfo(getString(R.string.current_temp), main?.temp ?: "-"),
            WeatherInfo(getString(R.string.weather_conditions), weather?.get(0)?.description ?: "-",weather?.get(0)?.icon ?: ""),
            WeatherInfo(getString(R.string.wind_speed), wind?.speed ?: "-"),
            WeatherInfo(getString(R.string.humidity), main?.humidity ?: "-"),
            WeatherInfo(getString(R.string.sunrise_time), getDate(sys.sunrise) ?: "-"),
            WeatherInfo(getString(R.string.sunset_time), getDate(sys.sunset) ?: "-"),
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