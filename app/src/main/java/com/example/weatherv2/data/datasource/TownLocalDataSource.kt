package com.example.weatherv2.data.datasource

import com.example.weatherv2.data.storage.TownDao
import com.example.weatherv2.domain.model.Town
import javax.inject.Inject

class TownLocalDataSource @Inject constructor(private val dao: TownDao) {

    fun getAllTowns() = dao.getAllTowns()

    fun addTown(town: Town) = dao.addTown(town)

    fun getTown(name: String) = dao.getTown(name)

    fun removeTown(id: String) = dao.removeTown(id)

    fun emptyDb() = dao.emptyDb()
}