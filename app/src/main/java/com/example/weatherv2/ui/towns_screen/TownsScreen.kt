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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weatherv2.domain.model.Town
import kotlin.random.Random

@Preview
@Composable
fun TownsScreen() {
    var townsList = remember {
        mutableStateListOf<Town>(
            Town("1","Moscow"),
            Town("3","Simferopol"),
        )
    }
    Column(modifier = Modifier.fillMaxHeight().background(Color.White)) {
        TownList(list = townsList)
        var townName by remember {
            mutableStateOf("")
        }
        TextField(value = townName, onValueChange = {
            townName = it
        })
        Button(onClick = {
            if (townName.isNotEmpty()) townsList.add(Town(Random.nextInt().toString(), townName))
            townName = ""
        }) {
            Text(text = "Add")
        }
    }
}


@Composable
fun TownList(list: SnapshotStateList<Town>) {
    LazyColumn(
        Modifier
            .background(Color.White)
            .padding(top = 16.dp)
    ) {
        items(list) { item ->
            TownItem(town = item, onClick = {
                //Navigate to weather screen
            })
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}


@Composable
fun TownItem(town: Town, onClick: (id: String) -> Unit) {
    Text(text = town.name, Modifier.clickable {
        onClick(town.id)
    })
}


