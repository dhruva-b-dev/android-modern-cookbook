package com.dhruva.locationmanager

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun RequestLocationPermissionUsingRememberLauncherForActivityResult(
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit
) {
    //1. Create a launcher to request location permission
    val locationPermissionRequest = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionsMap ->
        //2. Check if the permission is granted
        val arePermissionsGranted = permissionsMap.values.reduce { acc, next ->
            acc && next
        }
        //3. Handle the permission result
        if (arePermissionsGranted) {
            onPermissionGranted.invoke()
        } else {
            onPermissionDenied.invoke()
        }
    }
    //4. Launch the permission request
    LaunchedEffect(Unit) {
        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        )
    }
}