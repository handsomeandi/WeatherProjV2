package com.example.weatherv2.domain

import com.example.weatherv2.data.repository.WeatherRepository
import javax.inject.Inject

class GetTownUseCase @Inject constructor(private val repository: WeatherRepository) {
    fun getTown(name: String) = repository.getTown(name)
}