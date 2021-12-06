package com.example.weatherv2

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.weatherv2.domain.model.TownWeather
import com.example.weatherv2.ui.weather_screen.WeatherUiState
import com.google.android.gms.maps.model.LatLng

object MainDestinations {
    const val SPLASH_SCREEN = "splashScreen"
    const val MAIN_APP = "mainApp"
    const val WEATHER_SCREEN = "weatherScreen"
    const val TOWNS_SCREEN = "townsScreen"
    const val INFO_SCREEN = "infoScreen"
    const val TOWN_NAME = "townName"
}

class WeatherNavigationActions(navController: NavHostController) {
    val navigateToWeatherScreen: (townName: String?) -> Unit = {
        navController.navigate("${MainDestinations.WEATHER_SCREEN}?${MainDestinations.TOWN_NAME}=$it") {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }

            launchSingleTop = true
            restoreState = true
        }
    }
    val navigateToTownsScreen: () -> Unit = {
        navController.navigate(MainDestinations.TOWNS_SCREEN) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }

            launchSingleTop = true
            restoreState = true
        }
    }
    val navigateToInfoScreen: () -> Unit = {
        navController.navigate(MainDestinations.INFO_SCREEN) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }

            launchSingleTop = true
            restoreState = true
        }
    }
}

@Composable
fun rememberWeatherAppState(
    currentTownWeather: TownWeather? = null,
    currentLocation: LatLng? = null
) =
    remember(currentTownWeather, currentLocation) {
        WeatherUiState(currentTownWeather, currentLocation)
    }

