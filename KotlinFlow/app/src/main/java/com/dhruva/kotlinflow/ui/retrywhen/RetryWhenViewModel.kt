package com.dhruva.kotlinflow.ui.retrywhen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dhruva.kotlinflow.di.DefaultDispatcher
import com.dhruva.kotlinflow.di.MainDispatcher
import com.dhruva.kotlinflow.ui.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class RetryWhenViewModel @Inject constructor(
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<String>>(UiState.Loading)

    val uiState: StateFlow<UiState<String>> = _uiState

    fun startTask() {
        viewModelScope.launch(mainDispatcher) {
            _uiState.value = UiState.Loading

            doLongRunningTask().flowOn(defaultDispatcher).retryWhen { cause, attempt ->
                if (cause is IOException && attempt < 3) {
                    delay(2000)
                    return@retryWhen true
                } else {
                    return@retryWhen false
                }
            }.catch {
                _uiState.value = UiState.Error("Something Went Wrong")
            }.collect {
                _uiState.value = UiState.Success("Task Completed")
            }
        }
    }

    fun doLongRunningTask(): Flow<Int> {
        return flow {
            delay(2000)
            val random = (0..2).random()
            if (random == 0) {
                throw IOException()
            } else if (random == 1) {
                throw IndexOutOfBoundsException()
            }

            delay(2000)
            emit(0)
        }
    }
}