package com.dhruva.designpatterns.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSerializable
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.dhruva.designpatterns.ui.screens.CreationalPatternsScreen
import com.dhruva.designpatterns.ui.screens.DesignPatternsScreen
import com.dhruva.designpatterns.ui.screens.StructuralPatternsScreen
import kotlinx.serialization.serializer

@Composable
fun rememberAppNavBackStack(vararg keys: AppNavigationKeys): NavBackStack<AppNavigationKeys> {
    return rememberSerializable(serializer = serializer()) {
        NavBackStack(*keys)
    }
}

@Composable
fun AppNavigator() {
    val backStack = rememberAppNavBackStack(DesignPatternsScreen)

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = { key ->
            when (key) {
                is DesignPatternsScreen -> NavEntry(key) {
                    DesignPatternsScreen(
                        navigateToCreationalScreen = {
                            backStack.add(CreationalScreen)
                        },
                        navigateToStructuralScreen = {
                            backStack.add(StructuralScreen)
                        },
                        navigateToBehaviouralScreen = {}
                    )
                }

                is CreationalScreen -> NavEntry(key) {
                    CreationalPatternsScreen()
                }

                is StructuralScreen -> NavEntry(key) {
                    StructuralPatternsScreen()
                }
            }
        }
    )
}