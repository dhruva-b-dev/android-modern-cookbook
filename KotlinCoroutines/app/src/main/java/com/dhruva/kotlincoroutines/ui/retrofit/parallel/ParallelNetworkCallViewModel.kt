package com.dhruva.kotlincoroutines.ui.retrofit.parallel

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
import javax.inject.Inject

@HiltViewModel
class ParallelNetworkCallViewModel @Inject constructor(
    private val userApi: UserApi
) :
    ViewModel() {

    private val uiState = MutableLiveData<UiState<List<UserDto>>>()

    val coroutineExceptionHandler = CoroutineExceptionHandler { _, e ->
        uiState.postValue(UiState.Error("Exception Handler exception $e"))
    }

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch(coroutineExceptionHandler) {
            uiState.postValue(UiState.Loading)

            val userFromApiDeferred = async { userApi.getUsers() }
            val userFromMoreApiDeferred = async { userApi.getMoreUsers() }

            val userFromApi = userFromApiDeferred.await()
            val userFromMoreApi = userFromMoreApiDeferred.await()

            val allUsers = mutableListOf<UserDto>()
            allUsers.addAll(userFromApi)
            allUsers.addAll(userFromMoreApi)

            uiState.postValue(UiState.Success(data = allUsers))
        }
    }

    fun getUiState(): LiveData<UiState<List<UserDto>>> {
        return uiState
    }
}