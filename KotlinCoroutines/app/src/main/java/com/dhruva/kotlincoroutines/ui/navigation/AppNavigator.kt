package com.dhruva.kotlincoroutines.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSerializable
import androidx.compose.ui.Modifier
import androidx.navigation.Navigator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.dhruva.kotlincoroutines.ListScreen
import com.dhruva.kotlincoroutines.ui.basic.BasicScreen
import com.dhruva.kotlincoroutines.ui.errorhandling.exceptionhandler.ExceptionHandlerScreen
import com.dhruva.kotlincoroutines.ui.errorhandling.supervisor.IgnoreErrorAndContinue
import com.dhruva.kotlincoroutines.ui.errorhandling.trycatch.TryAndCatchScreen
import com.dhruva.kotlincoroutines.ui.retrofit.parallel.ParallelNetworkCallScreen
import com.dhruva.kotlincoroutines.ui.retrofit.series.SeriesNetworkCallScreen
import com.dhruva.kotlincoroutines.ui.retrofit.single.SingleNetworkCallScreen
import com.dhruva.kotlincoroutines.ui.timeout.TimeoutScreen
import kotlinx.serialization.serializer

@Composable
fun rememberAppNavBackStack(vararg keys: AppNavigationKeys): NavBackStack<AppNavigationKeys> {
    return rememberSerializable(serializer = serializer()) {
        NavBackStack(*keys)
    }
}

@Composable
fun AppNavigator() {

    val backStack = rememberAppNavBackStack(List)

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = { key ->
            when (key) {
                List -> NavEntry(key) {
                    ListScreen(
                        modifier = Modifier,
                        onBasicScreenClicked = {
                            backStack.add(Basic)
                        },
                        onSingleScreenClicked = {
                            backStack.add(Single)
                        },
                        onParallelScreenClicked = {
                            backStack.add(Parallel)
                        },
                        onSeriesScreenClicked = {
                            backStack.add(Series)
                        }
                    )
                }

                Basic -> NavEntry(key) {
                    BasicScreen()
                }

                Parallel -> NavEntry(key) {
                    ParallelNetworkCallScreen()
                }

                Series -> NavEntry(key) {
                    SeriesNetworkCallScreen()
                }

                Single -> NavEntry(key) {
                    SingleNetworkCallScreen()
                }

                ExceptionHandler -> NavEntry(key) {
                    ExceptionHandlerScreen()
                }

                IgnoreErrorAndContinue -> NavEntry(key) {
                    IgnoreErrorAndContinue()
                }

                Timeout -> NavEntry(key) {
                    TimeoutScreen()
                }

                TryAndCatch -> NavEntry(key) {
                    TryAndCatchScreen()
                }
            }
        }
    )
}