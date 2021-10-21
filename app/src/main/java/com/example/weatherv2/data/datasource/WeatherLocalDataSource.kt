package com.example.weatherv2.data.datasource

import com.example.weatherv2.data.storage.TownDao
import com.example.weatherv2.domain.model.Town
import javax.inject.Inject

class WeatherLocalDataSource @Inject constructor(private val dao: TownDao) {

    fun getAllTowns() = dao.getAllTowns()

    suspend fun addTown(town: Town) = dao.addTown(town)

    fun getTown(name: String) = dao.getTown(name)

    suspend fun removeTown(id: String) = dao.removeTown(id)

    suspend fun emptyDb() = dao.emptyDb()
}