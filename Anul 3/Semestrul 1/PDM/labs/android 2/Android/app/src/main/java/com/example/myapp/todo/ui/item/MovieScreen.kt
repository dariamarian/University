package com.example.myapp.todo.ui

import android.Manifest
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapp.core.Result
import com.example.myapp.todo.ui.item.ItemViewModel
import com.example.myapp.ui.MyLocation
import com.example.myapp.ui.TAG
import com.example.myapp.util.Permissions
import com.example.myapp.util.showSimpleNotificationWithTapAction
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.android.gms.maps.model.LatLng


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemScreen(itemId: String?, onClose: () -> Unit, isOnline : Boolean) {
    val context = LocalContext.current
    val channelId = "MyTestChannel"
    val notificationId = 0
    val itemViewModel = viewModel<ItemViewModel>(factory = ItemViewModel.Factory(itemId))
    val itemUiState = itemViewModel.uiState


    var name by rememberSaveable { mutableStateOf(itemUiState.movie.name) }
    var year by rememberSaveable { mutableStateOf(itemUiState.movie.year) }
    var age by rememberSaveable { mutableStateOf(itemUiState.movie.age) }
    var errorText by remember { mutableStateOf("") }
    var explicit by rememberSaveable { mutableStateOf(itemUiState.movie.explicit) }
    var long by rememberSaveable { mutableStateOf(itemUiState.movie.long) }
    var lat by rememberSaveable { mutableStateOf(itemUiState.movie.lat) }


    LaunchedEffect(long) {
        Log.d(TAG, "long = $long, lat = $lat")
    }

    Log.d("ItemScreen", "recompose, text = $name")

    LaunchedEffect(itemUiState.submitResult) {
        Log.d("ItemScreen", "Submit = ${itemUiState.submitResult}");
        if (itemUiState.submitResult is Result.Success) {
            Log.d("ItemScreen", "Closing screen");
            onClose();
        }
    }

    var textInitialized by remember { mutableStateOf(itemId == null) }
    LaunchedEffect(itemId, itemUiState.loadResult) {
        Log.d("ItemScreen", "Text initialized = ${itemUiState.loadResult}");
        if (textInitialized) {
            return@LaunchedEffect
        }
        if (!(itemUiState.loadResult is Result.Loading)) {
            name=itemUiState.movie.name
            year=itemUiState.movie.year
            age=itemUiState.movie.age
            explicit=itemUiState.movie.explicit
            long=itemUiState.movie.long
            lat=itemUiState.movie.lat
            textInitialized = true
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Movie") },
                actions = {
                    Button(onClick = {
                        if (errorText.isNotEmpty()) {
                            return@Button
                        }
                        Log.d("ItemScreen", "save item id = $itemId with name = $name")
                        itemViewModel.saveOrUpdateItem(name, year, age, explicit,lat,long,isOnline)

                        if (isOnline) {
                            Log.d("ItemScreen", "show notification")
                            showSimpleNotificationWithTapAction(
                                context = context,
                                channelId = channelId,
                                notificationId = notificationId,
                                textTitle = "Movie saved",
                                textContent = "Movie with name $name saved"
                            )
                        }
                        else {
                            showSimpleNotificationWithTapAction(
                                context = context,
                                channelId = channelId,
                                notificationId = notificationId,
                                textTitle = "Movie saved locally",
                                textContent = "Movie with name $name saved locally. Sync when online"
                            )
                        }

                    }) { Text("Save") }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            if (itemUiState.loadResult is Result.Loading) {
                CircularProgressIndicator()
                return@Scaffold
            }
            if (itemUiState.submitResult is Result.Loading) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) { LinearProgressIndicator() }
            }
            if (itemUiState.loadResult is Result.Error) {
                Text(text = "Failed to load item - ${(itemUiState.loadResult as Result.Error).exception?.message}")
            }
            Row {

                TextField(
                    value = name,
                    onValueChange = { name = it }, label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth(),
                )
            }
            Row {
                TextField(
                    value = year,
                    onValueChange = {
                        year=it;
                        if(!isValidDate(year)){
                            errorText="Invalid date format"
                        }
                        else {

                            //errorText=""
                            if (isValidDate(year))
                                errorText = ""
                            else
                            {
                                errorText = "Invalid date format"
                            }
                        }


                    }, label = { Text("Year") },
                    modifier = Modifier.fillMaxWidth(),
                )
            }
            Row {
                TextField(
                    value = age.toString(),
                    onValueChange = {
                        try {
                            age=it.toInt()
                            errorText=""
                        }
                        catch (e: NumberFormatException) {
                            age=0
                            errorText="Invalid age format"
                        }
                                    }, label = { Text("Age") },
                    modifier = Modifier.fillMaxWidth(),
                )
            }
            Row {
                Checkbox(
                    checked = explicit,
                    onCheckedChange = { explicit = it },
                )
                Text(text = "Explicit")
            }
            Row{
                MapContent(lat = lat, long = long,
                    onMapLongClick = { latLng ->
                        lat = latLng.latitude
                        long = latLng.longitude
                    }
                    )
            }

            if (itemUiState.submitResult is Result.Error) {
                Text(
                    text = "Failed to submit item - ${(itemUiState.submitResult as Result.Error).exception?.message}",
                    modifier = Modifier.fillMaxWidth(),
                )
            }
            if (errorText.isNotEmpty()) {
                Text(
                    text = errorText,
                    color = Color.Red,
                    modifier = Modifier.padding(start = 16.dp, top = 8.dp)
                )
            }
        }
    }
}
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapContent(lat : Double?, long : Double?,
               onMapLongClick: (LatLng) -> Unit) {
    Permissions(
        permissions = listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        ),
        rationaleText = "Please allow app to use location (coarse or fine)",
        dismissedText = "O noes! No location provider allowed!"
    ) {
        MyLocation(lat, long, onMapLongClick)
    }
}


fun isValidDate(date: String): Boolean {
    val regex = """^(0[1-9]|[12][0-9]|3[01])\.(0[1-9]|1[0-2])\.\d{4}$""".toRegex()
    return regex.matches(date)
}

