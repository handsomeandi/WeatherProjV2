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
import kotlinx.coroutines.Dispatchers
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
    var currentLocation: LatLng? = null
)

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val getWeatherDataUseCase: GetWeatherDataUseCase,
    private val addTownUseCase: AddTownUseCase
) : BaseViewModel<WeatherIntent, WeatherUiState>() {

    private val _state: MutableStateFlow<WeatherUiState> = MutableStateFlow(WeatherUiState())
    val state: StateFlow<WeatherUiState>
        get() = _state

    override fun handleIntent() {
        viewModelScope.launch {
            intent.consumeAsFlow().collect { intent ->
                when (intent) {
                    is WeatherIntent.RequestWeather -> requestWeather(intent.townName)
                    else -> _state.update { state ->
                        state.copy()
                    }
                }
            }
        }
    }

    //TODO: fix logic of requesting location and displaying weather after that. Fix multiple calls on this method
    private fun requestWeather(townName: String?) {
        viewModelScope.launch {
            requestForLastKnownLocation()
            with(_state.value) {
                if (townName == null) {
                    _state.update {
                        it.copy().apply {
                            if (currentLocation != null) {
                                currentTownWeather = getWeatherDataUseCase.getCurrentLocationWeather(
                                    currentLocation!!.latitude.toString(),
                                    currentLocation!!.longitude.toString()
                                )
                            }else Log.d("testing", "No current location")
                        }
                    }
                    currentTownWeather?.town?.let {
                        addTownUseCase.addTown(it)
                    }
                } else {
                    _state.update {
                        it.copy().apply {
                            currentTownWeather = getWeatherDataUseCase.getWeatherData(townName)
                        }
                    }
                }
            }
        }
    }

    private suspend fun requestForLastKnownLocation() {
            val coordinates = getCurrentLocation()
            _state.update { state ->
                state.copy().apply {
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
}