package com.example.weatherv2.ui.towns_screen

sealed class TownsIntent {
    class AddTown(val townName: String) : TownsIntent()
    object GetAllTowns : TownsIntent()
}