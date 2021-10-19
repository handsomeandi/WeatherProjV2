package com.example.weatherv2

import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

object MainDestinations {
    const val WEATHER_SCREEN = "weatherScreen"
    const val TOWNS_SCREEN = "townsScreen"
    const val INFO_SCREEN = "infoScreen"
}

class WeatherNavigationActions(navController: NavHostController) {
    val navigateToWeatherScreen: () -> Unit = {
        navController.navigate(MainDestinations.WEATHER_SCREEN) {
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
