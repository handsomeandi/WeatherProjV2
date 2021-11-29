package com.example.weatherv2.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherv2.MainDestinations
import com.example.weatherv2.R
import com.example.weatherv2.ui.bottom_menu.BottomMenu
import com.example.weatherv2.ui.bottom_menu.BottomMenuContent
import com.example.weatherv2.ui.towns_screen.TownsScreen
import com.example.weatherv2.ui.towns_screen.TownsViewModel
import com.example.weatherv2.ui.weather_screen.WeatherScreen
import com.example.weatherv2.ui.weather_screen.WeatherViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@ExperimentalPermissionsApi
@Preview
@Composable
fun WeatherApp() {
    val navController = rememberNavController()
    //TODO: make bottom navigation item selection and state saving work
    Column(modifier = Modifier.fillMaxHeight()) {
        NavHost(
            navController = navController,
            startDestination = "${MainDestinations.WEATHER_SCREEN}?${MainDestinations.TOWN_NAME}={townName}",
            modifier = Modifier
                .weight(1.0f)
        ) {
            navGraph(navController)
        }
        Column(verticalArrangement = Arrangement.Bottom) {
            BottomMenu(
                items = listOf(
                    BottomMenuContent(
                        "Weather",
                        R.drawable.ic_launcher_foreground,
                        "${MainDestinations.WEATHER_SCREEN}?${MainDestinations.TOWN_NAME}={townName}"
                    ),
                    BottomMenuContent("Towns", R.drawable.ic_launcher_foreground, MainDestinations.TOWNS_SCREEN),
                    BottomMenuContent("Info", R.drawable.ic_launcher_foreground, MainDestinations.INFO_SCREEN)
                ), navController = navController
            )
        }
    }
}


@ExperimentalPermissionsApi
fun NavGraphBuilder.navGraph(navController: NavController) {
    composable("${MainDestinations.WEATHER_SCREEN}?${MainDestinations.TOWN_NAME}={townName}") { from ->
        val weatherViewModel = hiltViewModel<WeatherViewModel>()
        WeatherScreen(weatherViewModel, from.arguments?.getString(MainDestinations.TOWN_NAME))
        Log.d("testing", "weatherScreenCalled")
    }
    composable(MainDestinations.TOWNS_SCREEN) { from ->
        val townsViewModel = hiltViewModel<TownsViewModel>()
        TownsScreen(townsViewModel) {
            val route = "${MainDestinations.WEATHER_SCREEN}?${MainDestinations.TOWN_NAME}=$it"
            navController.navigate(route) {
                popUpTo(navController.graph.findStartDestination().id) {
                    inclusive = true
                }

            }
        }
        Log.d("testing", "townScreenCalled")
    }
    composable(MainDestinations.INFO_SCREEN) { from ->
        InfoScreen()
    }
}