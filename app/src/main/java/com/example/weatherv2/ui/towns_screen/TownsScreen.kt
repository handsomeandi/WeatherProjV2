package com.example.weatherv2.ui.towns_screen

import android.widget.Toast
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.weatherv2.R
import com.example.weatherv2.domain.model.Town
import com.example.weatherv2.ui.theme.DefaultButtonColor
import com.example.weatherv2.ui.theme.EditTextSelectedBackgroundColor
import com.example.weatherv2.ui.theme.LightGray
import com.example.weatherv2.ui.theme.TownEditTextBackgroundColor
import com.example.weatherv2.ui.theme.Typography
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@Composable
fun TownsScreen(viewModel: TownsViewModel, onTownClicked: (String) -> Unit) {
    val townsState by viewModel.state.collectAsState()
    val scope = rememberCoroutineScope()
    LaunchedEffect(townsState.townsList) {
        viewModel.intent.send(TownsIntent.GetAllTowns)
    }
    Column(
        modifier = Modifier
            .background(Color.White)
    ) {
        TownList(
            list = townsState.townsList, onTownClicked, modifier = Modifier
                .background(LightGray)
                .weight(1.0f)
                .padding(bottom = 10.dp),
            onRemoveClicked = {
                scope.launch {
                    viewModel.intent.send(TownsIntent.RemoveTown(it))
                }
            }
        )
        Divider(color = LightGray, thickness = 2.dp)
        Column(
            verticalArrangement = Arrangement.Bottom, modifier = Modifier.padding(
                bottom = 30.dp, top = 35.dp
            )
        ) {
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

@ExperimentalAnimationApi
@Composable
fun TownList(list: List<Town>, onTownClicked: (String) -> Unit, modifier: Modifier, onRemoveClicked: (String) -> Unit) {
    Box(modifier) {
        LazyColumn(Modifier) {
            items(list) { item ->
                TownItem(
                    town = item, onClick =
                    onTownClicked,
                    onRemoveClicked =
                    onRemoveClicked
                )

            }
        }
    }
}


@Composable
fun TownItem(town: Town, onClick: (id: String) -> Unit, onRemoveClicked: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(start = 10.dp, end = 10.dp, top = 10.dp)
            .background(Color.White)
            .clickable {
                onClick(town.name)
            }, verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = "• ${town.name}", style = Typography.h5, modifier = Modifier
                .padding(start = 20.dp, end = 20.dp)
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_delete_icon),
            contentDescription = "removeTown",
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(end = 20.dp)
                .size(25.dp)
                .clickable {
                    onRemoveClicked(town.id)
                }, tint = Color.Red
        )
    }
}


