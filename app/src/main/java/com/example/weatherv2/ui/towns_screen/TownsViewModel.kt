package com.example.weatherv2.ui.towns_screen

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.weatherv2.domain.AddTownUseCase
import com.example.weatherv2.domain.GetAllTownsUseCase
import com.example.weatherv2.domain.GetWeatherDataUseCase
import com.example.weatherv2.domain.model.Town
import com.example.weatherv2.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TownsUiState(
    var townsList: List<Town> = mutableListOf()
)

@HiltViewModel
class TownsViewModel @Inject constructor(
    private val getAllTownsUseCase: GetAllTownsUseCase,
    private val addTownUseCase: AddTownUseCase,
    private val getWeatherDataUseCase: GetWeatherDataUseCase
) : BaseViewModel<TownsIntent>() {

    private val _state: MutableStateFlow<TownsUiState> = MutableStateFlow(TownsUiState())
    val state: StateFlow<TownsUiState>
        get() = _state

    override fun handleIntent() {
        viewModelScope.launch {
            intent.consumeAsFlow().collect { intent ->
                when (intent) {
                    is TownsIntent.AddTown -> addTown(intent.townName)
                    TownsIntent.GetAllTowns -> getAllTowns()
//                    else -> _state.update { state ->
//                        state.copy()
//                    }
                }
            }
        }
    }

    private fun addTown(townName: String) {
        viewModelScope.launch {
            try {
                val weatherInfoForTown = viewModelScope.async { getWeatherDataUseCase.getWeatherData(townName) }
                weatherInfoForTown.join()
                val receivedTown = weatherInfoForTown.await().town
                _state.update {
                    it.copy().apply {
                        townsList = it.townsList + receivedTown
                    }
                }
                addTownUseCase.addTown(receivedTown)
            } catch (e: Exception) {
                Log.d("testing", e.message ?: "error receiving town")
            }
        }
    }

    private fun getAllTowns() {
        viewModelScope.launch {
            if (_state.value.townsList.isNullOrEmpty()) {
                getAllTownsUseCase.getAllTowns().collect { towns ->
                    _state.update {
                        it.copy().apply {
                            townsList = towns
                        }
                    }
                }
            }
        }
    }
}