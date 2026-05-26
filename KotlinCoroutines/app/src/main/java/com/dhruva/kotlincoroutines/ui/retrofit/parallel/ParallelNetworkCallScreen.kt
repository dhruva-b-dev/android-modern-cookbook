package com.dhruva.kotlincoroutines.ui.retrofit.parallel

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.dhruva.kotlincoroutines.data.dto.UserDto
import com.dhruva.kotlincoroutines.ui.base.UiState
import com.dhruva.kotlincoroutines.ui.components.LoadingIndicator
import com.dhruva.kotlincoroutines.ui.components.UserList

@Composable
fun ParallelNetworkCallScreen(viewModel: ParallelNetworkCallViewModel = hiltViewModel()) {
    // Observe LiveData as Compose State to trigger recomposition properly
    val uiState by viewModel.getUiState().observeAsState(UiState.Loading)

    Scaffold(
        topBar = {
            @OptIn(ExperimentalMaterial3Api::class)
            TopAppBar(
                title = { Text("Users") },
                actions = {}
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (val state = uiState) {
                is UiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        LoadingIndicator()
                    }
                }

                is UiState.Success<List<UserDto>> -> {
                    UserList(users = state.data)
                }

                is UiState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = state.message,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}