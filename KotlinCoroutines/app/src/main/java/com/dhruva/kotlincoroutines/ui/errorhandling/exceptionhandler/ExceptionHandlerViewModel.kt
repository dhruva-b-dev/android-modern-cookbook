package com.dhruva.kotlincoroutines.ui.errorhandling.exceptionhandler

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dhruva.kotlincoroutines.data.dto.UserDto
import com.dhruva.kotlincoroutines.data.remote.UserApi
import com.dhruva.kotlincoroutines.ui.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExceptionHandlerViewModel @Inject constructor(
    val userApi: UserApi
) : ViewModel() {

    private val uiState = MutableLiveData<UiState<List<UserDto>>>()

    val coroutineExceptionHandler = CoroutineExceptionHandler { _, e ->
        uiState.postValue(UiState.Error("Exception from handler:$e"))
    }

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch(coroutineExceptionHandler) {
            uiState.postValue(UiState.Loading)
            val userVal = userApi.getUsersWithError()
            uiState.postValue(UiState.Success(userVal))
        }
    }

    fun getUiState(): LiveData<UiState<List<UserDto>>> {
        return uiState
    }
}