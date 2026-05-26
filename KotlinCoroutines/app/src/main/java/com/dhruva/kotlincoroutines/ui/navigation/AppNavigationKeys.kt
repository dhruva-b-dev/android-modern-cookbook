package com.dhruva.kotlincoroutines.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface AppNavigationKeys : NavKey

@Serializable
data object List : AppNavigationKeys

@Serializable
data object Basic : AppNavigationKeys

@Serializable
data object Single : AppNavigationKeys

@Serializable
data object Parallel : AppNavigationKeys

@Serializable
data object Series : AppNavigationKeys
@Serializable
data object Timeout : AppNavigationKeys
@Serializable
data object ExceptionHandler : AppNavigationKeys
@Serializable
data object IgnoreErrorAndContinue : AppNavigationKeys

@Serializable
data object TryAndCatch : AppNavigationKeys
