package com.dhruva.kotlincoroutines.ui.retrofit.series

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dhruva.kotlincoroutines.data.dto.UserDto
import com.dhruva.kotlincoroutines.data.remote.UserApi
import com.dhruva.kotlincoroutines.ui.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeriesNetworkCallViewModel @Inject constructor(
    val userApi: UserApi
) : ViewModel() {

    private val uiState = MutableLiveData<UiState<List<UserDto>>>()

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            uiState.postValue(UiState.Loading)

            try {
                val userFromApi = userApi.getUsers()
                val userFromMoreApi = userApi.getMoreUsers()

                val allUsers = mutableListOf<UserDto>()
                allUsers.addAll(userFromApi)
                allUsers.addAll(userFromMoreApi)

                uiState.postValue(UiState.Success(allUsers))
            } catch (e: Exception) {
                uiState.postValue(UiState.Error("Something went wrong!"))
            }
        }
    }

    fun getUiState(): LiveData<UiState<List<UserDto>>> {
        return uiState
    }
}