package com.dhruva.locationmanager

import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class LocationUtilsTest {

    @Test
    fun `getLastUserLocation calls onSuccess when location is available`() {
        // Arrange
        val fusedClient = mockk<FusedLocationProviderClient>()
        val task = mockk<Task<Location>>()
        val location = mockk<Location>()
        
        every { location.latitude } returns 12.34
        every { location.longitude } returns 56.78
        every { fusedClient.lastLocation } returns task
        
        val successSlot = slot<OnSuccessListener<Location>>()
        every { task.addOnSuccessListener(capture(successSlot)) } returns task
        every { task.addOnFailureListener(any()) } returns task
        
        var successResult: Pair<Double, Double>? = null
        
        // Act
        LocationUtils.getLastUserLocation(
            fusedClient,
            onSuccess = { successResult = it },
            onFailure = {},
            onNull = {}
        )
        
        successSlot.captured.onSuccess(location)
        
        // Assert
        assertEquals(Pair(12.34, 56.78), successResult)
    }

    @Test
    fun `getLastUserLocation calls onNull when location is null`() {
        // Arrange
        val fusedClient = mockk<FusedLocationProviderClient>()
        val task = mockk<Task<Location>>()
        
        every { fusedClient.lastLocation } returns task
        
        val successSlot = slot<OnSuccessListener<Location?>>()
        every { task.addOnSuccessListener(capture(successSlot)) } returns task
        every { task.addOnFailureListener(any()) } returns task
        
        var onNullCalled = false
        
        // Act
        LocationUtils.getLastUserLocation(
            fusedClient,
            onSuccess = {},
            onFailure = {},
            onNull = { onNullCalled = true }
        )
        
        successSlot.captured.onSuccess(null)
        
        // Assert
        assertTrue(onNullCalled)
    }

    @Test
    fun `getCurrentLocation calls onFailure when task fails`() {
        // Arrange
        val fusedClient = mockk<FusedLocationProviderClient>()
        val task = mockk<Task<Location>>()
        val exception = Exception("Failed")
        
        every { fusedClient.getCurrentLocation(any<Int>(), null) } returns task
        
        val failureSlot = slot<OnFailureListener>()
        every { task.addOnSuccessListener(any()) } returns task
        every { task.addOnFailureListener(capture(failureSlot)) } returns task
        
        var failureException: Exception? = null
        
        // Act
        LocationUtils.getCurrentLocation(
            fusedClient,
            onSuccess = {},
            onFailure = { failureException = it }
        )
        
        failureSlot.captured.onFailure(exception)
        
        // Assert
        assertEquals("Failed", failureException?.message)
    }

    @Test
    fun `getCurrentLocation calls onSuccess when task succeeds`(){
        //Arrange
        val fusedClient = mockk<FusedLocationProviderClient>()
        val task = mockk<Task<Location>>()
        val location = mockk<Location>()

        every { location.latitude } returns 12.34
        every { location.longitude } returns 56.78
        every { fusedClient.getCurrentLocation(any<Int>(),null) } returns task

        val successSlot = slot<OnSuccessListener<Location>>()
        every { task.addOnSuccessListener(capture(successSlot)) } returns task
        every { task.addOnFailureListener(any()) } returns task

        var successResult: Pair<Double, Double>? = null

        //Act
        LocationUtils.getCurrentLocation(fusedClient,
            onSuccess = { successResult = it },
            onFailure = {})
        
        successSlot.captured.onSuccess(location)

        //Assert
        assertEquals(Pair(12.34,56.78),successResult)
    }
}
