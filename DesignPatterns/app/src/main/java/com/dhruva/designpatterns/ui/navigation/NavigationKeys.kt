package com.dhruva.designpatterns.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface AppNavigationKeys  : NavKey

@Serializable
data object DesignPatternsScreen : AppNavigationKeys

@Serializable
data object CreationalScreen : AppNavigationKeys

data object StructuralScreen : AppNavigationKeys
//Add remaining files as you add them