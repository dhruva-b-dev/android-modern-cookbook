package com.dhruva.kotlinflow.ui.retrofit.parallel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dhruva.kotlinflow.data.dto.UserDto
import com.dhruva.kotlinflow.data.remote.UserApi
import com.dhruva.kotlinflow.di.IoDispatcher
import com.dhruva.kotlinflow.di.MainDispatcher
import com.dhruva.kotlinflow.ui.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ParallelViewModel @Inject constructor(
    private val userApi: UserApi,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<List<UserDto>>>(UiState.Loading)

    val uiState: StateFlow<UiState<List<UserDto>>> = _uiState

    init {
        fetchUsers()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun fetchUsers() {
        viewModelScope.launch(mainDispatcher) {
            _uiState.value = UiState.Loading
            val userFlow = flow { emit(userApi.getUsers()) }
            val moreUserFlow = flow { emit(userApi.getMoreUsers()) }

            userFlow.zip(moreUserFlow) { userFromApi, moreUserFromApi ->
                val allUser = mutableListOf<UserDto>()
                allUser.addAll(userFromApi)
                allUser.addAll(moreUserFromApi)
                return@zip allUser
            }.catch { e ->
                _uiState.value = UiState.Error(e.toString())
            }.collect {
                _uiState.value = UiState.Success(it)
            }
        }
    }
}

