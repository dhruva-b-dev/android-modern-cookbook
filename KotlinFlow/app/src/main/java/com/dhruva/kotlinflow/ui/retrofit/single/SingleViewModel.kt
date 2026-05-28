package com.dhruva.kotlinflow.ui.retrofit.single

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dhruva.kotlinflow.data.dto.UserDto
import com.dhruva.kotlinflow.data.remote.UserApi
import com.dhruva.kotlinflow.di.IoDispatcher
import com.dhruva.kotlinflow.di.MainDispatcher
import com.dhruva.kotlinflow.ui.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SingleViewModel @Inject constructor(
    private val userApi: UserApi,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<List<UserDto>>>(UiState.Loading)

    val uiState: StateFlow<UiState<List<UserDto>>> = _uiState

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch(mainDispatcher) {
            _uiState.value = UiState.Loading
            flow { emit(userApi.getUsers()) }
                //.asFlow()
                .flowOn(ioDispatcher).catch { e ->
                    _uiState.value = UiState.Error(e.toString())
                }.collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }
}

