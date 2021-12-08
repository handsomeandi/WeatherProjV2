package com.example.weatherv2.ui.weather_screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.weatherv2.domain.AddTownUseCase
import com.example.weatherv2.domain.GetWeatherDataUseCase
import com.example.weatherv2.domain.model.TownWeather
import com.example.weatherv2.ui.base.BaseViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
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
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

data class WeatherUiState(
    var currentTownWeather: TownWeather? = null,
    var currentLocation: LatLng? = null,
    var isRefreshing: Boolean = false,
    var isLoading: Boolean = false,
)

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val getWeatherDataUseCase: GetWeatherDataUseCase,
    private val addTownUseCase: AddTownUseCase
) : BaseViewModel<WeatherIntent>() {

    private val _state: MutableStateFlow<WeatherUiState> = MutableStateFlow(WeatherUiState())
    val state: StateFlow<WeatherUiState>
        get() = _state

    override fun handleIntent() {
        viewModelScope.launch {
            intent.consumeAsFlow().collect { intent ->
                when (intent) {
                    is WeatherIntent.RequestWeather -> requestWeather(intent.townName)
                    WeatherIntent.Refresh -> onRefresh()
                }
            }
        }
    }

    private fun onRefresh() {
        viewModelScope.launch {
            updateUIState {
                isRefreshing = true
                try {
                    currentTownWeather =
                        getWeatherDataUseCase.getWeatherData(currentTownWeather!!.town.name)
                } catch (e: Exception) {
                    Log.d("testing", "Town is not received yet")
                } finally {
                    isRefreshing = false
                }
            }
        }
    }

    private fun requestWeather(townName: String?) {
        if (townName == null) {
            if (!_state.value.currentTownWeather?.town?.name.isNullOrEmpty()) return
            viewModelScope.launch {
                updateUIState {
                    isLoading = true
                }
                requestCurrentLocationWeather()
            }
        } else {
            if (townName == _state.value.currentTownWeather?.town?.name) return
            updateUIState {
                isLoading = true
            }
            updateUIState {
                currentTownWeather = getWeatherDataUseCase.getWeatherData(townName)
                isLoading = false
            }
        }

    }

    private suspend fun requestCurrentLocationWeather() {
        requestForLastKnownLocation()
        updateUIState {
            currentLocation?.let { coordinates ->
                coroutineScope {
                    val currentLocationWeather = async {
                        getWeatherDataUseCase.getCurrentLocationWeather(
                            coordinates.latitude.toString(),
                            coordinates.longitude.toString()
                        )
                    }
                    currentTownWeather = currentLocationWeather.await().also {
                        coroutineScope {
                            addTownUseCase.addTown(it.town)
                        }
                    }
                }
            }
            isLoading = false
        }
    }

    private suspend fun requestForLastKnownLocation() {
        val coordinates = getCurrentLocation()
        _state.update { state ->
            state.apply {
                currentLocation = coordinates
            }
        }
    }

    @SuppressLint("MissingPermission")
    private suspend fun getCurrentLocation() = suspendCoroutine<LatLng> { continuation ->

        fusedLocationProviderClient.lastLocation
            .addOnSuccessListener {
                it?.let { location ->
                    continuation.resume(LatLng(location.latitude, location.longitude))
                }
            }
            .addOnFailureListener {
                Log.d("testing", "No current location")
            }
            .addOnCanceledListener {
                Log.d("testing", "No current location")
            }

    }

    private fun updateUIState(update: suspend WeatherUiState.() -> Unit) {
        viewModelScope.launch {
            _state.update {
                it.copy().apply {
                    this.update()
                }
            }
        }
    }
}