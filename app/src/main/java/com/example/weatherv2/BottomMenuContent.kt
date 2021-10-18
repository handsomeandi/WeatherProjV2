package com.example.weatherv2

import androidx.annotation.DrawableRes

data class BottomMenuContent(
    val title: String,
    @DrawableRes val iconId: Int,
    val navigationRoute: String
)
