package com.example.myapp.ui

import android.app.Application
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.model.LatLng

@Composable
fun MyLocation(lat : Double?, long : Double?,
               onMapLongClick: (LatLng) -> Unit) {
    val myNetworkStatusViewModel = viewModel<MyLocationViewModel>(
        factory = MyLocationViewModel.Factory(
            LocalContext.current.applicationContext as Application
        )
    )
    if (lat != null && long != null) {
        MyMap(lat = lat, long = long, onMapLongClick = onMapLongClick)
    }
    else{
        val location = myNetworkStatusViewModel.uiState
        if (location != null) {
            MyMap(lat = location.latitude, long = location.longitude, onMapLongClick = onMapLongClick)
        }
        else{
            LinearProgressIndicator()
        }
    }
}
