package com.dhruva.designpatterns.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface AppNavigationKeys : NavKey

@Serializable
data object DesignPatternsScreen : AppNavigationKeys

@Serializable
data object CreationalScreen : AppNavigationKeys

@Serializable
data object StructuralScreen : AppNavigationKeys

@Serializable
data object BehavioralScreen : AppNavigationKeys
