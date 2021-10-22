package com.example.weatherv2.ui.bottom_menu

import androidx.annotation.DrawableRes

data class BottomMenuContent(
    val title: String,
    @DrawableRes val iconId: Int,
    val route: String
)
