package com.example.weatherv2.ui

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
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
    Box {
        NavHost(
            navController = navController,
            startDestination = "${MainDestinations.WEATHER_SCREEN}?${MainDestinations.TOWN_NAME}={townName}"
        ) {
            navGraph(navController)
        }
        BottomMenu(
            items = listOf(
                BottomMenuContent(
                    "Weather",
                    R.drawable.ic_launcher_foreground,
                    "${MainDestinations.WEATHER_SCREEN}?${MainDestinations.TOWN_NAME}={townName}"
                ),
                BottomMenuContent("Towns", R.drawable.ic_launcher_foreground, MainDestinations.TOWNS_SCREEN),
                BottomMenuContent("Info", R.drawable.ic_launcher_foreground, MainDestinations.INFO_SCREEN)
            ), navController = navController, modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}


@ExperimentalPermissionsApi
fun NavGraphBuilder.navGraph(navController: NavController) {
    //TODO: create shared weather viewModel that contains current town and current location
//    val sharedViewMode: SharedViewModel = hiltViewModel<SharedViewModel>()

    composable("${MainDestinations.WEATHER_SCREEN}?${MainDestinations.TOWN_NAME}={townName}") { from ->
        val weatherViewModel = hiltViewModel<WeatherViewModel>()
        WeatherScreen(weatherViewModel, from.arguments?.getString(MainDestinations.TOWN_NAME))
        Log.d("testing", "weatherScreenCalled")
    }
    composable(MainDestinations.TOWNS_SCREEN) { from ->
        val townsViewModel = hiltViewModel<TownsViewModel>()
        TownsScreen(townsViewModel) {
            navController.navigate("${MainDestinations.WEATHER_SCREEN}?${MainDestinations.TOWN_NAME}=$it")
        }
        Log.d("testing", "townScreenCalled")
    }
    composable(MainDestinations.INFO_SCREEN) { from ->
        InfoScreen()
    }
}