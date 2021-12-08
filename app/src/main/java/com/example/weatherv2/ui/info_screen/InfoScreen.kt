package com.example.weatherv2.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun InfoScreen() {
    Column(Modifier.fillMaxHeight().fillMaxWidth().background(Color.White).padding(horizontal = 30.dp, vertical = 20.dp), horizontalAlignment = Alignment.Start) {
        Text(text = "Имя: Дегтярев Артур Русланович", fontSize = 20.sp)
        Text(text = "Телефон: +79788003158", fontSize = 20.sp)
        Text(text = "Библиотеки: Compose, Navigation Component, SqlDelight, Retrofit, Hilt, Coroutines, Gson ", fontSize = 20.sp)
    }
}