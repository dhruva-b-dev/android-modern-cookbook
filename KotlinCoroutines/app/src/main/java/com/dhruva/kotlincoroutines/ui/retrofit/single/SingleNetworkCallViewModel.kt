package com.dhruva.kotlincoroutines.ui.retrofit.single

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dhruva.kotlincoroutines.data.dto.UserDto
import com.dhruva.kotlincoroutines.data.remote.UserApi
import com.dhruva.kotlincoroutines.ui.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class SingleNetworkCallViewModel @Inject constructor(
    private val userApi: UserApi
) : ViewModel() {

    private val uiState = MutableLiveData<UiState<List<UserDto>>>()

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            uiState.postValue(UiState.Loading)
            try {
                val user = userApi.getUsers()
                uiState.postValue(UiState.Success(user))
            } catch (e: Exception) {
                uiState.postValue(UiState.Error(e.toString()))
            }
        }
    }

    fun getUiState(): LiveData<UiState<List<UserDto>>> {
        return uiState
    }
}