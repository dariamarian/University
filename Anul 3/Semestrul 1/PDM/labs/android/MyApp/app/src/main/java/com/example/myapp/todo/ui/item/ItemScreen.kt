package com.example.myapp.todo.ui

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapp.R
import com.example.myapp.core.Result
import com.example.myapp.todo.ui.item.ItemViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemScreen(itemId: String?, onClose: () -> Unit) {
    val itemViewModel = viewModel<ItemViewModel>(factory = ItemViewModel.Factory(itemId))
    val itemUiState = itemViewModel.uiState
    var title by rememberSaveable { mutableStateOf(itemUiState.item.title) }
    var genre by rememberSaveable { mutableStateOf(itemUiState.item.genre) }
    var releaseDate by rememberSaveable { mutableStateOf(itemUiState.item.releaseDate) }
    var rating by rememberSaveable { mutableStateOf(itemUiState.item.rating.toString()) }
    var watched by rememberSaveable { mutableStateOf(itemUiState.item.watched) }
    Log.d("ItemScreen", "recompose, title = $title")

    LaunchedEffect(itemUiState.submitResult) {
        Log.d("ItemScreen", "Submit = ${itemUiState.submitResult}");
        if (itemUiState.submitResult is Result.Success) {
            Log.d("ItemScreen", "Closing screen");
            onClose();
        }
    }

    var titleInitialized by remember { mutableStateOf(itemId == null) }
    var genreInitialized by remember { mutableStateOf(itemId == null) }
    var releaseDateInitialized by remember { mutableStateOf(itemId == null) }
    var ratingInitialized by remember { mutableStateOf(itemId == null) }
    var watchedInitialized by remember { mutableStateOf(itemId == null) }
    LaunchedEffect(itemId, itemUiState.loadResult) {
        Log.d("ItemScreen", "Title initialized = ${itemUiState.loadResult}");
        if (titleInitialized && genreInitialized && releaseDateInitialized && ratingInitialized && watchedInitialized) {
            return@LaunchedEffect
        }
        if (!(itemUiState.loadResult is Result.Loading)) {
            title = itemUiState.item.title
            titleInitialized = true
            genre = itemUiState.item.genre
            genreInitialized = true
            releaseDate = itemUiState.item.releaseDate
            releaseDateInitialized = true
            rating = itemUiState.item.rating.toString()
            ratingInitialized = true
            watched = itemUiState.item.watched
            watchedInitialized = true
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.item)) },
                actions = {
                    Button(onClick = {
                        Log.d("ItemScreen", "save item title = $title");
                        itemViewModel.saveOrUpdateItem(
                            title,
                            genre,
                            releaseDate,
                            rating.toInt(),
                            watched
                        )
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
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            Row {
                // TextField for Genre
                TextField(
                    value = genre,
                    onValueChange = { genre = it },
                    label = { Text("Genre") },
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            Row {
                // TextField for Release Date
                TextField(
                    value = releaseDate,
                    onValueChange = { releaseDate = it },
                    label = { Text("Release Date") },
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            Row {
                // TextField for Rating
                TextField(
                    value = rating,
                    onValueChange = { rating = it },
                    label = { Text("Rating") },
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            Row {
                // Checkbox for Watched
                Checkbox(
                    checked = watched,
                    onCheckedChange = { watched = it },
                    modifier = Modifier.padding(16.dp)
                )
                Text(text = "Watched")
            }

            if (itemUiState.submitResult is Result.Error) {
                Text(
                    text = "Failed to submit item - ${(itemUiState.submitResult as Result.Error).exception?.message}",
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}


@Preview
@Composable
fun PreviewItemScreen() {
    ItemScreen(itemId = "0", onClose = {})
}
