package com.dhruva.locationmanager

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.os.Build
import androidx.annotation.RequiresPermission
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import java.util.Locale
import kotlin.coroutines.resume

object LocationUtils {

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    fun getLastUserLocation(
        fusedLocationProviderClient: FusedLocationProviderClient,
        onSuccess: (Pair<Double, Double>) -> Unit,
        onFailure: (Exception) -> Unit,
        onNull: () -> Unit
    ) {
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
            location?.let {
                onSuccess(Pair(it.latitude, it.longitude))
            } ?: onNull()
        }.addOnFailureListener {
            onFailure(it)
        }
    }

    @SuppressLint("MissingPermission")
    fun getCurrentLocation(
        fusedLocationProviderClient: FusedLocationProviderClient,
        onSuccess: (Pair<Double, Double>) -> Unit,
        onFailure: (Exception) -> Unit,
        priority: Int = Priority.PRIORITY_HIGH_ACCURACY
    ) {
        fusedLocationProviderClient.getCurrentLocation(priority, null)
            .addOnSuccessListener { location ->
                location?.let {
                    onSuccess(Pair(it.latitude, it.longitude))
                } ?: onFailure(Exception("Location is null"))
            }.addOnFailureListener {
                onFailure(it)
            }
    }

    suspend fun getLocationName(
        context: Context,
        latitude: Double,
        longitude: Double
    ): String? = withContext(Dispatchers.IO) {
        if (!Geocoder.isPresent()) return@withContext null
        val geocoder = Geocoder(context, Locale.getDefault())
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                suspendCancellableCoroutine { continuation ->
                    geocoder.getFromLocation(latitude, longitude, 1) { addresses ->
                        if (addresses.isNotEmpty()) {
                            val address = addresses[0]
                            continuation.resume(
                                address.locality ?: address.adminArea ?: address.getAddressLine(0)
                            )
                        } else {
                            continuation.resume(null)
                        }
                    }
                }
            } else {
                @Suppress("DEPRECATION")
                val addresses = geocoder.getFromLocation(latitude, longitude, 1)
                if (!addresses.isNullOrEmpty()) {
                    val address = addresses[0]
                    address.locality ?: address.adminArea ?: address.getAddressLine(0)
                } else {
                    null
                }
            }
        } catch (e: Exception) {
            null
        }
    }
}
