package com.dhruva.locationmanager

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.dhruva.locationmanager.ui.theme.LocationManagerTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        setContent {
            LocationManagerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    // State variables to manage location information and permission result text
                    var locationText by remember { mutableStateOf("No location obtained :(") }
                    var showPermissionResultText by remember { mutableStateOf(false) }
                    var permissionResultText by remember { mutableStateOf("Permission Granted...") }

                    // Request location permission using a Compose function
                    RequestLocationPermissionUsingRememberLauncherForActivityResult(
                        onPermissionGranted = {
                            // Callback when permission is granted
                            showPermissionResultText = true
                            permissionResultText = "Permission Granted..."
                            // Attempt to get the last known user location
                            LocationUtils.getLastUserLocation(
                                fusedLocationProviderClient,
                                onSuccess = {
                                    lifecycleScope.launch {
                                        locationText = LocationUtils.getLocationName(
                                            this@MainActivity,
                                            it.first,
                                            it.second
                                        )
                                            ?: "Location using LAST-LOCATION: LATITUDE: ${it.first}, LONGITUDE: ${it.second}"
                                    }
                                },
                                onFailure = { exception ->
                                    showPermissionResultText = true
                                    locationText =
                                        exception.localizedMessage ?: "Error Getting Last Location"
                                },
                                onNull = {
                                    // Attempt to get the current user location
                                    LocationUtils.getCurrentLocation(
                                        fusedLocationProviderClient,
                                        onSuccess = {
                                            lifecycleScope.launch {
                                                locationText = LocationUtils.getLocationName(
                                                    this@MainActivity,
                                                    it.first,
                                                    it.second
                                                )
                                                    ?: "Location using CURRENT-LOCATION: LATITUDE: ${it.first}, LONGITUDE: ${it.second}"
                                            }
                                        },
                                        onFailure = {
                                            showPermissionResultText = true
                                            locationText = it.localizedMessage
                                                ?: "Error Getting Current Location"
                                        },
                                    )
                                },
                            )
                        },
                        onPermissionDenied = {
                            // Callback when permission is denied
                            showPermissionResultText = true
                            permissionResultText = "Permission Denied :("
                        },
                    )

                    // Compose UI layout using a Column
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Display a message indicating the permission request process
                        Text(
                            text = "Requesting location permission...", textAlign = TextAlign.Center
                        )

                        // Display permission result and location information if available
                        if (showPermissionResultText) {
                            Text(text = permissionResultText, textAlign = TextAlign.Center)
                            Text(text = locationText, textAlign = TextAlign.Center)
                        }
                    }
                }
            }
        }
    }

    private fun areLocationPermissionsGranted(): Boolean {
        return ActivityCompat.checkSelfPermission(
            this, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            this, Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }
}
