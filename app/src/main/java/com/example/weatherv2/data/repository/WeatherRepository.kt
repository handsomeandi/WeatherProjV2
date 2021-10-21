package com.example.weatherv2.data.repository

import com.example.weatherv2.data.Mapper.toTown
import com.example.weatherv2.data.Mapper.toTownWeather
import com.example.weatherv2.data.datasource.WeatherLocalDataSource
import com.example.weatherv2.data.datasource.WeatherRemoteDataSource
import com.example.weatherv2.domain.model.Town
import com.example.weatherv2.domain.model.TownWeather
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val localDataSource: WeatherLocalDataSource,
    private val remoteDataSource: WeatherRemoteDataSource
) {
    suspend fun getWeatherData(townName: String): TownWeather {
        val response = remoteDataSource.getWeatherData(townName)
        return response.toTownWeather()
    }

    suspend fun getCurrentLocationWeather(lat: String, lon: String): TownWeather {
        val response = remoteDataSource.getCurrentLocationWeather(lat, lon)
        return response.toTownWeather()
    }

    fun getAllTowns() = localDataSource.getAllTowns().map {
        it.map { entity ->
            entity.toTown()
        }
    }

    suspend fun addTown(town: Town) = localDataSource.addTown(town)

    fun getTown(name: String) = localDataSource.getTown(name).map { it?.toTown() }

    suspend fun removeTown(id: String) = localDataSource.removeTown(id)

    suspend fun emptyDb() = localDataSource.emptyDb()

}