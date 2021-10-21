package com.example.weatherv2.data.storage

import com.example.weatherv2.TownItemsQueries
import com.example.weatherv2.domain.model.Town
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOneOrNull
import javax.inject.Inject

class TownDao @Inject constructor(private val queries: TownItemsQueries) {

    fun getAllTowns() = queries.selectAll().asFlow().mapToList()

    suspend fun addTown(town: Town) = queries.insertOrReplace(town.id, town.name)

    fun getTown(name: String) = queries.selectByName(name).asFlow().mapToOneOrNull()

    suspend fun removeTown(id: String) = queries.deleteTown(id)

    suspend fun emptyDb() = queries.empty()
}