package com.example.weatherv2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherv2.AppExt.setNavigationBar
import com.example.weatherv2.AppExt.setStatusBarColor
import com.example.weatherv2.ui.SplashScreen
import com.example.weatherv2.ui.WeatherApp
import com.example.weatherv2.ui.theme.Weatherv2Theme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalPermissionsApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Weatherv2Theme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = MainDestinations.SPLASH_SCREEN
                ) {
                    composable(MainDestinations.SPLASH_SCREEN) {
                        SplashScreen {
                            navController.navigate(MainDestinations.MAIN_APP) {
                                popUpTo(MainDestinations.MAIN_APP) {
                                    inclusive = true
                                }
                            }
                            setStatusBarColor(R.color.white, false)
                            setNavigationBar(R.color.navigationBarBackgroundColor)
                        }
                    }
                    composable(MainDestinations.MAIN_APP) {
                        WeatherApp()
                    }
                }
            }
        }
    }
}
