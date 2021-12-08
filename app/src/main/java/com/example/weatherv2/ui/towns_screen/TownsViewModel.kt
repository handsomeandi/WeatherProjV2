package com.example.weatherv2.ui.towns_screen

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.weatherv2.domain.AddTownUseCase
import com.example.weatherv2.domain.GetAllTownsUseCase
import com.example.weatherv2.domain.GetWeatherDataUseCase
import com.example.weatherv2.domain.RemoveTownUseCase
import com.example.weatherv2.domain.model.Town
import com.example.weatherv2.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TownsUiState(
    var townsList: List<Town> = mutableListOf(),
    var errorMessage: String = ""
)

@HiltViewModel
class TownsViewModel @Inject constructor(
    private val getAllTownsUseCase: GetAllTownsUseCase,
    private val addTownUseCase: AddTownUseCase,
    private val getWeatherDataUseCase: GetWeatherDataUseCase,
    private val removeTownUseCase: RemoveTownUseCase,
) : BaseViewModel<TownsIntent, TownsUiState>() {

    override val _state: MutableStateFlow<TownsUiState> = MutableStateFlow(TownsUiState())
    val state: StateFlow<TownsUiState>
        get() = _state

    init {
        handleIntent()
    }

    override fun handleIntent() {
        viewModelScope.launch {
            intent.consumeAsFlow().collect { intent ->
                when (intent) {
                    is TownsIntent.AddTown -> addTown(intent.townName)
                    TownsIntent.GetAllTowns -> getAllTowns()
                    is TownsIntent.RemoveTown -> removeTown(intent.townId)
                }
            }
        }
    }

    private fun addTown(townName: String) {
        viewModelScope.launch {
            try {
                if (townName.isEmpty()) throw Exception("пустое поле города")
                val weatherInfoForTown = viewModelScope.async { getWeatherDataUseCase.getWeatherData(townName) }
                val receivedTown = weatherInfoForTown.await().town.also {
                    coroutineScope {
                        addTownUseCase.addTown(it)
                    }
                }
                updateUIState {
                    townsList = townsList + receivedTown

                }

            } catch (e: Exception) {
                updateUIState {
                    errorMessage = "Ошибка: ${e.message}"
                }
                Log.d("testing", e.message ?: "error receiving town")
            }
        }
    }

    private fun getAllTowns() {
        viewModelScope.launch {
            if (_state.value.townsList.isNullOrEmpty()) {
                getAllTownsUseCase.getAllTowns().collect { towns ->
                    updateUIState {
                        townsList = towns
                    }
                }
            }
        }
    }

    private fun removeTown(id: String) {
        viewModelScope.launch {
            try {
                removeTownUseCase.removeTown(id)
            } catch (e: Exception) {
                Log.d("testing", e.message ?: "error removing town")
            }
        }
    }

    override fun updateUIState(update: suspend TownsUiState.() -> Unit) {
        viewModelScope.launch {
            _state.update {
                it.copy().apply {
                    update()
                }
            }
        }
    }
}