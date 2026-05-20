package com.dhruva.modernandroidarchitectures_mvi.feature.user_modified_mvi.account

import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dhruva.modernandroidarchitectures_mvi.R
import com.dhruva.modernandroidarchitectures_mvi.feature.user_modified_mvi.base.ErrorDialogType
import com.dhruva.modernandroidarchitectures_mvi.feature.user_modified_mvi.base.ErrorState
import com.dhruva.modernandroidarchitectures_mvi.feature.user_modified_mvi.base.createErrorState
import com.dhruva.modernandroidarchitectures_mvi.feature.user_modified_mvi.base.dismiss
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserAccountViewModel : ViewModel(), UserAccountContract {

    private val username = mutableStateOf("")
    private val points = mutableLongStateOf(0L)

    private val _viewState = snapshotFlow {
        UserAccountContract.State(
            username = username.value,
            points = points.longValue
        )
    }

    override val viewState: StateFlow<UserAccountContract.State> = _viewState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        UserAccountContract.State.createInitial()
    )

    private val _effect = MutableSharedFlow<UserAccountContract.Effect>()
    override val effect: Flow<UserAccountContract.Effect> = _effect

    private fun emitEffect(effect: UserAccountContract.Effect) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }

    override fun addPoints() {
        points.longValue++
    }

    override fun subtractPoints() {
        points.longValue--
    }

    override fun goBack() {
        emitEffect(UserAccountContract.Effect.GoBack)
    }

    override fun doSth() {
        // Heavy code
    }

    override fun changeUsername(username: String) {
        this.username.value = username
    }

    private val _errorState = MutableStateFlow<ErrorState?>(null)
    override val errorState: StateFlow<ErrorState?> = _errorState

    override fun showError() {
        _errorState.update {
            _errorState.createErrorState(
                title = R.string.error_common_title,
                description = R.string.error_common_description,
                onDismiss = _errorState::dismiss,
                onTryAgainAction = {
                    _errorState.dismiss()
                    viewModelScope.launch {
                        delay(500)
                        showError()
                    }
                },
                type = ErrorDialogType.COMMON
            )
        }
    }
}