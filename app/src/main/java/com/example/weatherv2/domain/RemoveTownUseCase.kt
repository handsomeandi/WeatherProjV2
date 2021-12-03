package com.example.weatherv2.domain

import com.example.weatherv2.data.repository.WeatherRepository
import javax.inject.Inject

class RemoveTownUseCase @Inject constructor(private val repository: WeatherRepository) {
    fun removeTown(id: String) = repository.removeTown(id)
}