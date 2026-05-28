package com.dhruva.kotlinflow.ui.flowon

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun FlowOnScreen(viewModel: FlowOnViewModel = hiltViewModel()){
    viewModel.startFlowOnTask()
}