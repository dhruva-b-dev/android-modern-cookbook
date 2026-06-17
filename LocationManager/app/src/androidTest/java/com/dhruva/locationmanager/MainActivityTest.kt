package com.dhruva.locationmanager

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.GrantPermissionRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @get:Rule
    val permissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )

    @Test
    fun testInitialStateAndPermissionGranted() {
        // Since we grant permission, onPermissionGranted should be called
        // and "Permission Granted..." should eventually appear
        composeTestRule.onNodeWithText("Requesting location permission...").assertExists()
        
        // We wait for the "Permission Granted..." text which is set in onPermissionGranted
        composeTestRule.waitUntil(5000) {
            try {
                composeTestRule.onNodeWithText("Permission Granted...").assertExists()
                true
            } catch (e: AssertionError) {
                false
            }
        }
    }
}
