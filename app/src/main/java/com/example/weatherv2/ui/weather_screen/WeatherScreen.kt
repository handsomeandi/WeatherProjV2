package com.example.weatherv2.ui.weather_screen

import androidx.compose.foundation.background
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.example.weatherv2.ui.theme.Typography

@Composable
fun WeatherScreen(viewModel: WeatherViewModel) {
    var weatherInfoList = remember {
        mutableStateListOf(
            WeatherInfo("test", "test info1"),
            WeatherInfo("test", "test info2"),
            WeatherInfo("test", "test info3")
        )
    }
    Column(
        Modifier
            .background(Color.White)
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        InfoList(
            list = weatherInfoList
        )

    }

}

@Composable
fun InfoList(list: SnapshotStateList<WeatherInfo>) {
    LazyColumn(
        Modifier
            .background(Color.White)
            .padding(top = 16.dp)
    ) {
        items(list) { item ->
            WeatherInfoItem(label = item.label, info = item.info)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun WeatherInfoItem(label: String, info: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(text = "$label:", style = Typography.h5, modifier = Modifier.padding(start = 16.dp, end = 16.dp))
        Text(text = info, style = Typography.h5)
    }
}