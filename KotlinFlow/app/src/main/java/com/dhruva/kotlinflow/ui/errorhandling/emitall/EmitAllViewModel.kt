package com.dhruva.kotlinflow.ui.errorhandling.emitall

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dhruva.kotlinflow.data.dto.UserDto
import com.dhruva.kotlinflow.data.remote.UserApi
import com.dhruva.kotlinflow.di.IoDispatcher
import com.dhruva.kotlinflow.di.MainDispatcher
import com.dhruva.kotlinflow.ui.base.UiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.emptyList

class EmitAllViewModel @Inject constructor(
    private val userApi: UserApi,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<UserDto>>>(UiState.Loading)

    val uiState: StateFlow<UiState<List<UserDto>>> = _uiState

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch(mainDispatcher) {
            _uiState.value = UiState.Loading

            val userApiFlow = flow { emit(userApi.getUsers()) }
            val userApiErrorFlow = flow { emit(userApi.getUsersWithError()) }

            userApiFlow.catch { emitAll(flowOf(emptyList())) }.zip(
                userApiErrorFlow.catch { emitAll(flowOf(emptyList())) },
            ) { usersFromApi, moreUsersFromApi ->
                val allUser = mutableListOf<UserDto>()
                allUser.addAll(usersFromApi)
                allUser.addAll(moreUsersFromApi)
                return@zip allUser
            }.flowOn(ioDispatcher).catch { e ->
                _uiState.value = UiState.Error(e.toString())
            }.collect {
                _uiState.value = UiState.Success(it)
            }
        }
    }
}

