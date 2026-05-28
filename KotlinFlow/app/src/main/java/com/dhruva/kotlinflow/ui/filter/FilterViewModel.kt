package com.dhruva.kotlinflow.ui.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dhruva.kotlinflow.di.IoDispatcher
import com.dhruva.kotlinflow.di.MainDispatcher
import com.dhruva.kotlinflow.ui.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(
    @MainDispatcher val mainDispatcher: CoroutineDispatcher,
    @IoDispatcher val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<String>>(UiState.Loading)

    val uiState: StateFlow<UiState<String>> = _uiState

    fun startFilterTask() {
        viewModelScope.launch(mainDispatcher) {
            _uiState.value = UiState.Loading

            val result = mutableListOf<Int>()
            (1..5).asFlow()
                .filter {
                    it % 2 == 0
                }.toList(result)

            _uiState.value = UiState.Success(result.toString())
        }
    }

}