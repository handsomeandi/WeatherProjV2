package com.example.weatherv2.ui.towns_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.weatherv2.domain.model.Town
import kotlinx.coroutines.launch

@Composable
fun TownsScreen(viewModel: TownsViewModel, onTownClicked: (String) -> Unit) {
    val townsState by viewModel.state.collectAsState()
    LaunchedEffect(townsState.townsList) {
        viewModel.intent.send(TownsIntent.GetAllTowns)
    }
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(Color.White)
    ) {
        TownList(list = townsState.townsList, onTownClicked)
        Column {
            val scope = rememberCoroutineScope()
            var townName by remember {
                mutableStateOf("")
            }
            TextField(value = townName, onValueChange = {
                townName = it
            })
            Button(onClick = {
                scope.launch {
                    viewModel.intent.send(TownsIntent.AddTown(townName))
                    townName = ""
                }
//            if (townName.isNotEmpty()) townsList.add(Town(Random.nextInt().toString(), townName))
//            townName = ""
            }) {
                Text(text = "Add")
            }
        }

    }
}


@Composable
fun TownList(list: List<Town>, onTownClicked: (String) -> Unit) {
    LazyColumn(
        Modifier
            .background(Color.White)
            .padding(top = 16.dp)
    ) {
        items(list) { item ->
            TownItem(town = item, onClick = {
                onTownClicked(it)
            })
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}


@Composable
fun TownItem(town: Town, onClick: (id: String) -> Unit) {
    Text(text = town.name, Modifier.clickable {
        onClick(town.name)
    })
}


