package com.example.weatherv2

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Preview
@Composable
fun WeatherApp() {
    val navController = rememberNavController()

    Box {
        NavHost(navController = navController, startDestination = "weatherScreen") {
            navGraph()
        }
        BottomMenu(
            items = listOf(
                BottomMenuContent("Weather", R.drawable.ic_launcher_foreground, MainDestinations.WEATHER_SCREEN),
                BottomMenuContent("Towns", R.drawable.ic_launcher_foreground, MainDestinations.TOWNS_SCREEN),
                BottomMenuContent("Info", R.drawable.ic_launcher_foreground, MainDestinations.INFO_SCREEN)
            ), Modifier.align(Alignment.BottomCenter),navController = navController
        )
    }
}

@Composable
fun BottomMenu(
    items: List<BottomMenuContent>,
    modifier: Modifier = Modifier,
    activeHighlightColor: Color = Color.Blue,
    activeTextColor: Color = Color.White,
    inactiveTextColor: Color = Color.LightGray,
    initialSelectedItemIndex: Int = 0,
    navController: NavHostController
) {
    var selectedItemIndex by remember {
        mutableStateOf(initialSelectedItemIndex)
    }
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Cyan)
            .padding(15.dp)
    ) {
        items.forEachIndexed { index, item ->
            BottomMenuItem(
                item = item,
                isSelected = index == selectedItemIndex,
                activeHighlightColor = activeHighlightColor,
                activeTextColor = activeTextColor,
                inactiveTextColor = inactiveTextColor
            ) {
                selectedItemIndex = index
                navController.navigate(item.navigationRoute)
            }
        }
    }
}

@Composable
fun BottomMenuItem(
    item: BottomMenuContent,
    isSelected: Boolean = false,
    activeHighlightColor: Color = Color.Blue,
    activeTextColor: Color = Color.White,
    inactiveTextColor: Color = Color.LightGray,
    onItemClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.clickable {
            onItemClick()
        }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(if (isSelected) activeHighlightColor else Color.Transparent)
                .padding(10.dp)
        ) {
            Icon(
                painter = painterResource(id = item.iconId),
                contentDescription = item.title,
                tint = if (isSelected) activeTextColor else inactiveTextColor,
                modifier = Modifier.size(20.dp)
            )
        }
        Text(
            text = item.title,
            color = if (isSelected) activeTextColor else inactiveTextColor
        )
    }
}


fun NavGraphBuilder.navGraph() {
    composable(MainDestinations.WEATHER_SCREEN) { from ->
        WeatherScreen()
    }
    composable(MainDestinations.TOWNS_SCREEN) { from ->
        TownsScreen()
    }
    composable(MainDestinations.INFO_SCREEN) { from ->
        InfoScreen()
    }
}