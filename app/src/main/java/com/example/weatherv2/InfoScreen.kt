package com.example.weatherv2

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun InfoScreen() {
    Column(Modifier.fillMaxHeight().fillMaxWidth().background(Color.White), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "InfoScreen", fontSize = 20.sp)
    }
}