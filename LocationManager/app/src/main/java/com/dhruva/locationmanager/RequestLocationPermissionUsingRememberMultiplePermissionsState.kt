package com.dhruva.locationmanager

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestLocationPermissionUsingRememberMultiplePermissionsState(
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit,
    onPermissionRevoked: () -> Unit
) {

    val permissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
    ) { permissionsMap ->

        val arePermissionGranted = permissionsMap.values.reduce { acc, next ->
            acc && next
        }

        if (arePermissionGranted) {
            onPermissionGranted.invoke()
        } else {
            onPermissionDenied.invoke()
        }
    }

    LaunchedEffect(key1 = permissionState) {

        val allPermissionsRevoked = permissionState.permissions.size ==
                permissionState.revokedPermissions.size

        val permissionsToRequest = permissionState.permissions.filter {
            !it.status.isGranted
        }

        if (permissionsToRequest.isNotEmpty()) permissionState.launchMultiplePermissionRequest()

        if (allPermissionsRevoked) {
            onPermissionRevoked()
        } else {
            if (permissionState.allPermissionsGranted) {
                onPermissionGranted()
            } else {
                onPermissionDenied()
            }
        }
    }
}