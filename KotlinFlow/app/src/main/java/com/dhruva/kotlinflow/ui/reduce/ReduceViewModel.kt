package com.dhruva.kotlinflow.ui.reduce

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dhruva.kotlinflow.data.remote.UserApi
import com.dhruva.kotlinflow.di.MainDispatcher
import com.dhruva.kotlinflow.ui.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReduceViewModel @Inject constructor(
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher
) : ViewModel(){

    private val _uiState = MutableStateFlow<UiState<String>>(UiState.Loading)

    val uiState : StateFlow<UiState<String>> = _uiState

    fun startReduceTask(){
        viewModelScope.launch(mainDispatcher) {
            _uiState.value = UiState.Loading
            val result = (1..5).asFlow()
                .reduce { a,b -> a+b }
            _uiState.value = UiState.Success(result.toString())
        }
    }
}