package com.example.weatherv2.ui.towns_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TownsUiState(
    val townsList: List<Town> = emptyList()
)

@HiltViewModel
class TownsViewModel @Inject constructor() : ViewModel() {
    val intent = Channel<TownsIntent>(Channel.UNLIMITED)

    private val _state: MutableStateFlow<TownsUiState> = MutableStateFlow(TownsUiState())
    private val state: StateFlow<TownsUiState>
        get() = _state

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            intent.consumeAsFlow().collect { intent ->
                when (intent) {
                    else -> _state.update { state ->
                        state.copy()
                    }
                }
            }
        }
    }
}