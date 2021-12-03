package com.example.weatherv2.domain

import com.example.weatherv2.data.repository.WeatherRepository
import javax.inject.Inject

class EmptyDbUseCase @Inject constructor(private val repository: WeatherRepository) {
    fun emptyDb() = repository.emptyDb()
}