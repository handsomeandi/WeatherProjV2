package com.example.weatherv2.ui.towns_screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.weatherv2.domain.model.Town
import com.example.weatherv2.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun TownsScreen(viewModel: TownsViewModel, onTownClicked: (String) -> Unit) {
    val townsState by viewModel.state.collectAsState()
    LaunchedEffect(townsState.townsList) {
        viewModel.intent.send(TownsIntent.GetAllTowns)
    }
    Column(
        modifier = Modifier
            .background(Color.White)
    ) {
        TownList(
            list = townsState.townsList, onTownClicked, modifier = Modifier
                .background(Color.White)
                .weight(1.0f)
                .padding(bottom = 10.dp)
        )
        Divider(color = LightGray, thickness = 2.dp)
        Column(
            verticalArrangement = Arrangement.Bottom, modifier = Modifier.padding(
                bottom = 30.dp, top = 35.dp
            )
        ) {
            val scope = rememberCoroutineScope()
            var townName by remember {
                mutableStateOf("")
            }
            TextField(value = townName, onValueChange = {
                townName = it
            }, modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 30.dp, start = 20.dp, end = 20.dp), textStyle = Typography.h6,
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black,
                    focusedLabelColor = Color.Black,
                    backgroundColor = TownEditTextBackgroundColor,
                    focusedIndicatorColor = EditTextSelectedBackgroundColor,
                    cursorColor = EditTextSelectedBackgroundColor
                ),
                label = { Text("Город") })
            Button(
                onClick = {
                    scope.launch {
                        viewModel.intent.send(TownsIntent.AddTown(townName))
                        townName = ""
                    }
                },
                Modifier
                    .width(200.dp)
                    .height(60.dp)
                    .align(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(20),
                colors = ButtonDefaults.buttonColors(backgroundColor = DefaultButtonColor, contentColor = Color.White)
            ) {
                Text(text = "Добавить", style = Typography.h5)
            }
        }
        ShowError(errorMessage = townsState.errorMessage)
    }
}


@Composable
fun ShowError(errorMessage: String) {
    if (errorMessage.isNotEmpty()) Toast.makeText(LocalContext.current, errorMessage, Toast.LENGTH_LONG).show()
}

@Composable
fun TownList(list: List<Town>, onTownClicked: (String) -> Unit, modifier: Modifier) {
    Box(modifier) {
        LazyColumn(Modifier) {
            items(list) { item ->
                TownItem(town = item, onClick = {
                    onTownClicked(it)
                })
            }
        }
    }

}


@Composable
fun TownItem(town: Town, onClick: (id: String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .clickable {
                onClick(town.name)
            }, verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "• ${town.name}", style = Typography.h5, modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, top = 15.dp)
        )
    }
}


