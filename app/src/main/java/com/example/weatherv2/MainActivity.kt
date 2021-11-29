package com.example.weatherv2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.graphics.toArgb
import com.example.weatherv2.AppExt.setNavigationBar
import com.example.weatherv2.AppExt.setStatusBarColor
import com.example.weatherv2.ui.WeatherApp
import com.example.weatherv2.ui.theme.BottomMenuBackgroundColor
import com.example.weatherv2.ui.theme.Weatherv2Theme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalPermissionsApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBarColor(R.color.white, false)
        setNavigationBar(R.color.navigationBarBackgroundColor)
        setContent {
            Weatherv2Theme {
                WeatherApp()
            }
        }
    }
}
