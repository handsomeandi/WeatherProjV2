package com.example.weatherv2.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherv2.MainDestinations
import com.example.weatherv2.R
import com.example.weatherv2.WeatherNavigationActions
import com.example.weatherv2.ui.bottom_menu.BottomMenu
import com.example.weatherv2.ui.bottom_menu.BottomMenuContent
import com.example.weatherv2.ui.towns_screen.TownsScreen
import com.example.weatherv2.ui.weather_screen.WeatherScreen
import com.example.weatherv2.ui.weather_screen.WeatherViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@ExperimentalPermissionsApi
@Preview
@Composable
fun WeatherApp() {
    val navController = rememberNavController()

    Box {
        val navigationActions = remember(navController) {
            WeatherNavigationActions(navController)
        }
        NavHost(navController = navController, startDestination = "weatherScreen") {
            navGraph()
        }
        BottomMenu(
            items = listOf(
                BottomMenuContent(
                    "Weather",
                    R.drawable.ic_launcher_foreground,
                    navigationActions.navigateToWeatherScreen
                ),
                BottomMenuContent("Towns", R.drawable.ic_launcher_foreground, navigationActions.navigateToTownsScreen),
                BottomMenuContent("Info", R.drawable.ic_launcher_foreground, navigationActions.navigateToInfoScreen)
            ), Modifier.align(Alignment.BottomCenter)
        )
    }
}


@ExperimentalPermissionsApi
fun NavGraphBuilder.navGraph() {
    composable(MainDestinations.WEATHER_SCREEN) { from ->
        val weatherViewModel = hiltViewModel<WeatherViewModel>()
        WeatherScreen(weatherViewModel, from.arguments?.getString(MainDestinations.TOWN_NAME))
    }
    composable(MainDestinations.TOWNS_SCREEN) { from ->
        TownsScreen()
    }
    composable(MainDestinations.INFO_SCREEN) { from ->
        InfoScreen()
    }
}