package com.example.weatherv2.ui.weather_screen

import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.weatherv2.AppExt.getString
import com.example.weatherv2.R
import com.example.weatherv2.domain.model.TownWeather
import com.example.weatherv2.ui.theme.Typography
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionsRequired
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@ExperimentalPermissionsApi
@Composable
fun WeatherScreen(viewModel: WeatherViewModel, townName: String?) {
    val weatherUiState by viewModel.state.collectAsState()
    val mapPermissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        )
    )

    Column(
        Modifier
            .background(Color.White)
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PermissionsRequired(
            multiplePermissionsState = mapPermissionsState,
            permissionsNotGrantedContent = {
                SideEffect {
                    mapPermissionsState.launchMultiplePermissionRequest()
                }
            },
            permissionsNotAvailableContent = {

            },
        ) {
            LaunchedEffect(weatherUiState.currentTownWeather) {
                viewModel.intent.send(WeatherIntent.RequestWeather(townName))
            }
            weatherUiState.currentTownWeather?.let {
                InfoList(
                    weather = it
                )
            }
        }


    }

}

@Composable
fun InfoList(weather: TownWeather) {
    Text(text = weather.town.name, style = Typography.h4, modifier = Modifier.padding(vertical = 10.dp))
    LazyColumn(
        Modifier
            .background(Color.White)
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
    ) {
        items(weather.weatherInfo) { item ->
            when(item.label){
                getString(R.string.current_temp) -> CurrentTempItem(info = item.info)
                getString(R.string.wind_speed) -> WeatherInfoItem(label = item.label, info = "${item.info} м/с")
                getString(R.string.humidity) -> WeatherInfoItem(label = item.label, info = "${item.info} %")
                else -> WeatherInfoItem(label = item.label, info = item.info)

            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun WeatherInfoItem(label: String, info: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = "$label:", style = Typography.h5, modifier = Modifier.padding(start = 16.dp, end = 16.dp), fontWeight = FontWeight.Bold)
        Text(text = info.replaceFirstChar {
            it.uppercase()
        }, style = Typography.h5, modifier = Modifier.align(Alignment.CenterHorizontally))
    }
}

@Composable
fun CurrentTempItem(info: String){
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
        Text(text = if(info.toFloat() < 0) "" else "+", style = Typography.h3, fontWeight = FontWeight.Light)
        Text(text = "$info °C", style = Typography.h2)
    }
}
