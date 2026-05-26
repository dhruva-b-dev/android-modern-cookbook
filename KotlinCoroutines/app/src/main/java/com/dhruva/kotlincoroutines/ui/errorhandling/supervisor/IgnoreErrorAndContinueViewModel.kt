package com.dhruva.kotlincoroutines.ui.errorhandling.supervisor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dhruva.kotlincoroutines.data.dto.UserDto
import com.dhruva.kotlincoroutines.data.remote.UserApi
import com.dhruva.kotlincoroutines.ui.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import javax.inject.Inject

@HiltViewModel
class IgnoreErrorAndContinueViewModel @Inject constructor(
    val userApi: UserApi
) : ViewModel() {
    private val uiState = MutableLiveData<UiState<List<UserDto>>>()

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            uiState.postValue(UiState.Loading)

            supervisorScope {

                val userFromApiDeferred = async { userApi.getUsersWithError() }
                val userFromMoreApiDeferred = async { userApi.getMoreUsers() }

                val userFromApi = try {
                    userFromApiDeferred.await()
                } catch (e: Exception) {
                    emptyList()
                }

                val userFromMoreApi = try {
                    userFromMoreApiDeferred.await()
                } catch (e: Exception) {
                    emptyList()
                }

                val allUser = mutableListOf<UserDto>()
                allUser.addAll(userFromApi)
                allUser.addAll(userFromMoreApi)

                uiState.postValue(UiState.Success(allUser))
            }
        }
    }

    fun getUiState(): LiveData<UiState<List<UserDto>>> {
        return uiState
    }
}