package com.example.myapp.todo.ui.items

import SyncJobViewModel
import android.app.Application
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.delay


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemsScreen(onItemClick: (id: String?) -> Unit, onAddItem: () -> Unit, onLogout: () -> Unit, isOnline : Boolean) {
    Log.d("ItemsScreen", "recompose")

    val myJobsViewModel = viewModel<SyncJobViewModel>(
        factory = SyncJobViewModel.Factory(
            LocalContext.current.applicationContext as Application
        )
    )

    val context = LocalContext.current
    val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    val lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

    val lightSensorListener = remember {
        object : SensorEventListener {
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

            override fun onSensorChanged(event: SensorEvent) {
                if (event.sensor.type == Sensor.TYPE_LIGHT) {
                    val lux = event.values[0]
                    if (lux > 3000) {
                        // Display pop-up
                        Toast.makeText(context, "It's too bright!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    DisposableEffect(Unit) {
        sensorManager.registerListener(lightSensorListener, lightSensor, SensorManager.SENSOR_DELAY_NORMAL)
        onDispose {
            sensorManager.unregisterListener(lightSensorListener)
        }
    }

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
            AnimatedFloatingActionButton(onAddItem)
        }
    ) {
        ItemList(
            movieList = itemsUiState,
            onItemClick = onItemClick,
            modifier = Modifier.padding(it)
        )
    }
}

@Composable
fun AnimatedFloatingActionButton(onClick: () -> Unit) {
    var isClicked by remember { mutableStateOf(false) }
    val scale = remember { Animatable(1f) }
    val rotation = remember { Animatable(0f) }
    val color = remember { Animatable(0f) }

    LaunchedEffect(isClicked) {
        if (isClicked) {
            // Enlarge the button with rotation and color change
            scale.animateTo(
                targetValue = 2f,
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            )
            rotation.animateTo(
                targetValue = 360f,
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            )
            color.animateTo(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            )
            delay(300) // Delay to allow the animation to be visible
            onClick() // Perform the onClick action after the delay
            isClicked = false // Reset the click state

            // Reset the values for the next click
            scale.snapTo(1f)
            rotation.snapTo(0f)
            color.snapTo(0f)
        }
    }

    FloatingActionButton(
        onClick = { isClicked = true },
        modifier = Modifier.graphicsLayer(
            scaleX = scale.value,
            scaleY = scale.value,
            rotationZ = rotation.value
        )
    ) {
        Icon(
            Icons.Rounded.Add,
            "Add",
            tint = if (color.value > 0.5f) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary
        )
    }
}


