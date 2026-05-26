package com.dhruva.kotlincoroutines.ui.timeout

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dhruva.kotlincoroutines.data.dto.UserDto
import com.dhruva.kotlincoroutines.data.remote.UserApi
import com.dhruva.kotlincoroutines.ui.base.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import javax.inject.Inject

@HiltViewModel
class TimeoutViewModel @Inject constructor(
    private val userApi: UserApi
)  : ViewModel(){
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
            try {
                withTimeout(100){
                    val userVal = userApi.getUsers()
                    uiState.postValue(UiState.Success(userVal))
                }
            }catch (et: TimeoutCancellationException) {
                uiState.postValue(UiState.Error("TimeoutCancellationException"))
            }
            catch (e: Exception) {
                uiState.postValue(UiState.Error("Something went wrong!"))
            }
        }
    }

    fun getUiState(): LiveData<UiState<List<UserDto>>> {
        return uiState
    }
}