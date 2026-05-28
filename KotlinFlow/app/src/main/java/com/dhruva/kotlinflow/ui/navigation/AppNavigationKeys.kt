package com.dhruva.kotlinflow.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface AppNavigationKeys : NavKey

@Serializable
data object List : AppNavigationKeys

@Serializable
data object Completion : AppNavigationKeys
//
@Serializable
data object Catch : AppNavigationKeys

@Serializable
data object EmitAll : AppNavigationKeys

@Serializable
data object Filter : AppNavigationKeys
@Serializable
data object FlowOn : AppNavigationKeys
@Serializable
data object Map : AppNavigationKeys
@Serializable
data object Reduce : AppNavigationKeys

@Serializable
data object Series : AppNavigationKeys
@Serializable
data object Single : AppNavigationKeys
@Serializable
data object Parallel : AppNavigationKeys
@Serializable
data object Retry : AppNavigationKeys
@Serializable
data object RetryWhen : AppNavigationKeys
@Serializable
data object RetryExponentialBackOff : AppNavigationKeys
@Serializable
data object RoomDB : AppNavigationKeys