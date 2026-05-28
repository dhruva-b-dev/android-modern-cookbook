package com.dhruva.kotlinflow.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dhruva.kotlinflow.data.dto.UserDto
import com.dhruva.kotlinflow.data.remote.UserApi
import com.dhruva.kotlinflow.di.IoDispatcher
import com.dhruva.kotlinflow.di.MainDispatcher
import com.dhruva.kotlinflow.model.User
import com.dhruva.kotlinflow.ui.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch

@HiltViewModel
class MapViewModel @Inject constructor(
    private val userApi: UserApi,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<User>>>(UiState.Loading)

    val uiState: StateFlow<UiState<List<User>>> = _uiState

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch(mainDispatcher) {
            _uiState.value = UiState.Loading
            flow { emit(userApi.getUsers()) }.map { apiUserList ->
                val users = mutableListOf<User>()
                for (apiUser in apiUserList) {
                    val user = User(
                        id = apiUser.id,
                        name = apiUser.name,
                        email = apiUser.email,
                        avatar = apiUser.avatar,
                    )
                    users.add(user)
                }
                users
            }.flowOn(ioDispatcher).catch { e -> _uiState.value = UiState.Error(e.toString()) }
                .collect { _uiState.value = UiState.Success(data = it) }
        }
    }
}


