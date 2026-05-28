package com.dhruva.kotlinflow.ui.roomDB

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dhruva.kotlinflow.data.dto.UserDto
import com.dhruva.kotlinflow.data.local.dao.UserDao
import com.dhruva.kotlinflow.data.local.entity.UserEntity
import com.dhruva.kotlinflow.data.remote.UserApi
import com.dhruva.kotlinflow.di.IoDispatcher
import com.dhruva.kotlinflow.di.MainDispatcher
import com.dhruva.kotlinflow.model.User
import com.dhruva.kotlinflow.ui.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomDBViewModel @Inject constructor(
    private val userApi: UserApi,
    private val userDao: UserDao,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<List<User>>>(UiState.Loading)

    val uiState: StateFlow<UiState<List<User>>> = _uiState

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch(mainDispatcher) {
            _uiState.value = UiState.Loading
            val userDBFlow = flow { emit(userDao.getAll()) }
            val userApiFlow = flow { emit(userApi.getUsers()) }

            userDBFlow.flatMapConcat { userFromDB ->
                if (userFromDB.isEmpty()) {
                    return@flatMapConcat userApiFlow.map { apiUserList ->
                        val userList = mutableListOf<User>()
                        for (userApi in apiUserList) {
                            val user = User(
                                id = userApi.id,
                                name = userApi.name,
                                email = userApi.email,
                                avatar = userApi.avatar
                            )
                            userList.add(user)
                        }
                        userList
                    }.flatMapConcat { userToInsertInDB ->
                        flow {
                            userDao.insertAll(userToInsertInDB.map { UserEntity.fromDomain(it) })
                            emit(userToInsertInDB)
                        }
                    }
                } else {
                    return@flatMapConcat flow {
                        emit(userFromDB.map { it.toDomain() })
                    }
                }

            }.flowOn(ioDispatcher).catch { e ->
                _uiState.value = UiState.Error(e.toString())
            }.collect {
                _uiState.value = UiState.Success(it)
            }
        }
    }
}

