package com.example.weatherv2.domain

import com.example.weatherv2.data.repository.WeatherRepository
import com.example.weatherv2.domain.model.Town
import javax.inject.Inject

class AddTownUseCase @Inject constructor(private val repository: WeatherRepository) {
    fun addTown(town: Town) = repository.addTown(town)
}