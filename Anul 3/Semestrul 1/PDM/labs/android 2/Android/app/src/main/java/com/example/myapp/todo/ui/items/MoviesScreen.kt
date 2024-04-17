package com.example.myapp.todo.ui.items


import SyncJobViewModel
import android.app.Application
import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.myapp.util.ConnectivityManagerNetworkMonitor
import com.example.myapp.util.SyncWorker
import java.util.concurrent.TimeUnit
import kotlin.math.log


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemsScreen(onItemClick: (id: String?) -> Unit, onAddItem: () -> Unit, onLogout: () -> Unit, isOnline : Boolean) {
    Log.d("ItemsScreen", "recompose")

    val myJobsViewModel = viewModel<SyncJobViewModel>(
        factory = SyncJobViewModel.Factory(
            LocalContext.current.applicationContext as Application
        )
    )

    LaunchedEffect(isOnline) {
        if (isOnline) {
            myJobsViewModel.enqueueWorker()
        } else {
            myJobsViewModel.cancelWorker()
        }
    }


    val itemsViewModel = viewModel<MoviesViewModel>(factory = MoviesViewModel.Factory)
    val itemsUiState by itemsViewModel.uiState.collectAsStateWithLifecycle(
        initialValue = listOf()
    )
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Movies") },
                actions = {
                    Button(onClick = onLogout) { Text("Logout") }
                }
            )
        },
        bottomBar = {
            if (isOnline) {
                Text(text = "Online")
            } else {
                Text(text = "Offline")
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    Log.d("ItemsScreen", "add")
                    onAddItem()
                },
            ) { Icon(Icons.Rounded.Add, "Add") }
        }
    ) {
        ItemList(
            movieList = itemsUiState,
            onItemClick = onItemClick,
            modifier = Modifier.padding(it)
        )
    }
}

