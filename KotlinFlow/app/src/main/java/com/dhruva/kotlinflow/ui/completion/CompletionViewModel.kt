package com.dhruva.kotlinflow.ui.completion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dhruva.kotlinflow.data.remote.UserApi
import com.dhruva.kotlinflow.di.IoDispatcher
import com.dhruva.kotlinflow.di.MainDispatcher
import com.dhruva.kotlinflow.ui.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

@HiltViewModel
class CompletionViewModel @Inject constructor(
    private val userApi: UserApi,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<String>>(UiState.Loading)

    val uiState: StateFlow<UiState<String>> = _uiState

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch(mainDispatcher) {
            _uiState.value = UiState.Loading
            flow {
                emit(userApi.getUsers())
            }.flowOn(ioDispatcher).catch { e ->
                _uiState.value = UiState.Error(e.toString())
            }.onCompletion {
                _uiState.value = UiState.Success("Task Completed")
            }.collect {


            }
        }
    }
}

