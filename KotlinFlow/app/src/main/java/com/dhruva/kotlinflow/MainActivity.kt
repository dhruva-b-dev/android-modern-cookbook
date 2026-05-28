package com.dhruva.kotlinflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.dhruva.kotlinflow.ui.components.CommonButtonComponent
import com.dhruva.kotlinflow.ui.navigation.AppNavigator
import com.dhruva.kotlinflow.ui.theme.KotlinFlowTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KotlinFlowTheme {

                AppNavigator()

            }
        }
    }
}


@Composable
fun ListScreen(
    modifier: Modifier,
    onCompletionScreenClicked: () -> Boolean,
    onCatchClicked: () -> Boolean,
    onEmitAllClicked: () -> Boolean,
    onFilterClicked: () -> Boolean,
    onFlowOnClicked: () -> Boolean,
    onMapClicked: () -> Boolean,
    onReduceClicked: () -> Boolean,
    onParallelClicked: () -> Boolean,
    onSeriesClicked: () -> Boolean,
    onSingleClicked: () -> Boolean,
    onRetryClicked: () -> Boolean,
    onRetryExponentialBackOffClicked: () -> Boolean,
    onRetryWhenClicked: () -> Boolean,
    onRoomDBClicked: () -> Boolean
) {
    val scope = rememberCoroutineScope()

    // Create and remember the scroll state
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .systemBarsPadding()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CommonButtonComponent(
            text = "Completion",
            onClick = { onCompletionScreenClicked() }
        )

        CommonButtonComponent(
            text = "Catch",
            onClick = { onCatchClicked() }
        )

        CommonButtonComponent(
            text = "EmitAll",
            onClick = { onEmitAllClicked() }
        )

        CommonButtonComponent(
            text = "Filter",
            onClick = { onFilterClicked() }
        )

        CommonButtonComponent(
            text = "FlowOn",
            onClick = { onFlowOnClicked() }
        )

        CommonButtonComponent(
            text = "Map",
            onClick = { onMapClicked() }
        )

        CommonButtonComponent(
            text = "Reduce",
            onClick = { onReduceClicked() }
        )

        CommonButtonComponent(
            text = "Parallel",
            onClick = { onParallelClicked() }
        )

        CommonButtonComponent(
            text = "Series",
            onClick = { onSeriesClicked() }
        )

        CommonButtonComponent(
            text = "Single",
            onClick = { onSingleClicked() }
        )

        CommonButtonComponent(
            text = "Retry",
            onClick = { onRetryClicked() }
        )

        CommonButtonComponent(
            text = "Retry Exponential BackOff",
            onClick = { onRetryExponentialBackOffClicked() }
        )

        CommonButtonComponent(
            text = "Retry When",
            onClick = { onRetryWhenClicked() }
        )

        CommonButtonComponent(
            text = "Room DB",
            onClick = { onRoomDBClicked() }
        )
    }
}
