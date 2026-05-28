package com.dhruva.kotlinflow.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSerializable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.dhruva.kotlinflow.ListScreen
import com.dhruva.kotlinflow.ui.completion.CatchScreen
import com.dhruva.kotlinflow.ui.completion.CompletionScreen
import com.dhruva.kotlinflow.ui.errorhandling.emitall.EmitAllScreen
import com.dhruva.kotlinflow.ui.filter.FilterScreen
import com.dhruva.kotlinflow.ui.flowon.FlowOnScreen
import com.dhruva.kotlinflow.ui.map.MapScreen
import com.dhruva.kotlinflow.ui.reduce.ReduceScreen
import com.dhruva.kotlinflow.ui.retrofit.parallel.ParallelScreen
import com.dhruva.kotlinflow.ui.retrofit.series.SeriesScreen
import com.dhruva.kotlinflow.ui.retrofit.single.SingleScreen
import com.dhruva.kotlinflow.ui.retry.RetryScreen
import com.dhruva.kotlinflow.ui.retryexponentialbackoff.RetryExponentialBackoffScreen
import com.dhruva.kotlinflow.ui.retrywhen.RetryWhenScreen
import com.dhruva.kotlinflow.ui.roomDB.RoomDBScreen
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
                        onCompletionScreenClicked = {
                            backStack.add(Completion)
                        },
                        onCatchClicked = {
                            backStack.add(Catch)
                        },
                        onEmitAllClicked = {
                            backStack.add(EmitAll)
                        },
                        onFilterClicked = {
                            backStack.add(Filter)
                        },
                        onFlowOnClicked = {
                            backStack.add(FlowOn)
                        },
                        onMapClicked = {
                            backStack.add(Map)
                        },
                        onReduceClicked = {
                            backStack.add(Reduce)
                        },
                        onParallelClicked = {
                            backStack.add(Parallel)
                        },
                        onSeriesClicked = {
                            backStack.add(Series)
                        },
                        onSingleClicked = {
                            backStack.add(Single)
                        },
                        onRetryClicked = {
                            backStack.add(Retry)
                        },
                        onRetryExponentialBackOffClicked = {
                            backStack.add(RetryExponentialBackOff)
                        },
                        onRetryWhenClicked = {
                            backStack.add(RetryWhen)
                        },
                        onRoomDBClicked = {
                            backStack.add(RoomDB)
                        }
                    )
                }

                Completion -> NavEntry(key) {
                    CompletionScreen()
                }

                Catch -> NavEntry(key) {
                    CatchScreen()
                }

                EmitAll -> NavEntry(key) {
                    EmitAllScreen()
                }

                Filter -> NavEntry(key) {
                    FilterScreen()
                }

                FlowOn -> NavEntry(key) {
                    FlowOnScreen()
                }

                Map -> NavEntry(key) {
                    MapScreen()
                }

                Reduce -> NavEntry(key) {
                    ReduceScreen()
                }

                Parallel -> NavEntry(key) {
                    ParallelScreen()
                }

                Series -> NavEntry(key) {
                    SeriesScreen()
                }

                Single -> NavEntry(key) {
                    SingleScreen()
                }

                Retry -> NavEntry(key){
                    RetryScreen()
                }
                RetryExponentialBackOff -> NavEntry(key){
                    RetryExponentialBackoffScreen()
                }
                RetryWhen -> NavEntry(key){
                    RetryWhenScreen()
                }
                RoomDB ->  NavEntry(key){
                    RoomDBScreen()
                }
            }
        }
    )
}
