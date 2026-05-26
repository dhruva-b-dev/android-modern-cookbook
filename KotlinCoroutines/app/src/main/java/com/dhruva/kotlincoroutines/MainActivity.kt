package com.dhruva.kotlincoroutines

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import com.dhruva.kotlincoroutines.ui.components.CommonButtonComponent
import com.dhruva.kotlincoroutines.ui.navigation.AppNavigator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavigator()
        }
    }
}

@Composable
fun ListScreen(
    modifier: Modifier = Modifier,
    onBasicScreenClicked: () -> Unit,
    onSingleScreenClicked: () -> Unit,
    onParallelScreenClicked: () -> Unit,
    onSeriesScreenClicked: () -> Unit
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
            text = "Basic",
            onClick = { onBasicScreenClicked() }
        )

        CommonButtonComponent(
            text = "Series",
            onClick = { onSeriesScreenClicked() }
        )

        CommonButtonComponent(
            text = "Single",
            onClick = { onSingleScreenClicked() }
        )

        CommonButtonComponent(
            text = "Parallel",
            onClick = { onParallelScreenClicked() }
        )
    }
}