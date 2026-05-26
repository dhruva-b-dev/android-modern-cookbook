package com.dhruva.kotlincoroutines.ui.room

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dhruva.kotlincoroutines.data.local.dao.UserDao
import com.dhruva.kotlincoroutines.data.local.entity.UserEntity
import com.dhruva.kotlincoroutines.data.remote.UserApi
import com.dhruva.kotlincoroutines.domain.model.User
import com.dhruva.kotlincoroutines.ui.base.UiState
import kotlinx.coroutines.launch
import javax.inject.Inject

class RoomViewModel @Inject constructor(
    private val userApi: UserApi,
    private val userDao: UserDao
) : ViewModel() {

    private val uiState = MutableLiveData<UiState<List<User>>>()

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            uiState.postValue(UiState.Loading)
            try {
                val usersFromDb = userDao.getAll()
                if (usersFromDb.isEmpty()) {
                    val usersFromApi = userApi.getUsers()
                    val usersToInsertInDB = mutableListOf<UserEntity>()
                    val users = mutableListOf<User>()

                    for (apiUser in usersFromApi) {
                        val userEntity = UserEntity(
                            apiUser.id,
                            apiUser.name,
                            apiUser.email,
                            apiUser.avatar
                        )
                        usersToInsertInDB.add(userEntity)
                        val user = User(
                            apiUser.id,
                            apiUser.name,
                            apiUser.email,
                            apiUser.avatar
                        )
                        users.add(user)
                    }

                    userDao.insertAll(usersToInsertInDB)

                    uiState.postValue(UiState.Success(users))

                } else {
                    //val users = usersFromDb.forEach { entity -> entity.toDomain() }
                    uiState.postValue(UiState.Success(emptyList()))
                }
            } catch (e: Exception) {
                uiState.postValue(UiState.Error("Something Went Wrong"))
            }
        }
    }

    fun getUiState(): LiveData<UiState<List<User>>> {
        return uiState
    }

}