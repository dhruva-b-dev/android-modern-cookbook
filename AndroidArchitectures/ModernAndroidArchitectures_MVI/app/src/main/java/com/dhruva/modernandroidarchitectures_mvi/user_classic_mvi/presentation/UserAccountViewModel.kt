package com.dhruva.modernandroidarchitectures_mvi.feature.user.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/*
* Classic MVI ViewModel
* Drawbacks :
* 1. Boilerplate code - having more Effects and Actions, code keeps getting harder and harder to read and maintain.
* 2. State as one Object - having State as just only one object makes it hard to read and update.
* 3. Action being a simple proxy - in a more complex cases, onAction method and things you do for
* each single case will grow, and eventually you will have to create a separate private function for it to even be able to read what each Action does.
* */
class UserAccountViewModel : ViewModel() {

    private val _viewState = MutableStateFlow(State.createInitial())
    val viewState: Flow<State> = _viewState

    private val _action = MutableStateFlow<Action>(Action.Idle)
    fun setAction(action: Action) {
        viewModelScope.launch {
            _action.update { action }
        }
    }

    private val _effect = MutableSharedFlow<Effect>()
    val effect: Flow<Effect> = _effect
    private fun emitEffect(effect: Effect) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }

    init {
        viewModelScope.launch {
            _action.collectLatest { onAction(it) }
        }
    }

    private fun onAction(action: Action) {
        when (action) {
            Action.AddPoints -> _viewState.update { it.copy(points = it.points + 1) }
            Action.Idle -> Unit
            Action.SubtractPoints -> _viewState.update { it.copy(points = it.points - 1) }
            Action.GoBack -> emitEffect(Effect.GoBack)
            Action.DoSth -> doSth()
            is Action.ChangeUsername -> _viewState.update { it.copy(username = action.username) }
        }
    }

    private fun doSth() {
        // Heavy code
    }

    data class State(val username: String, val points: Long) {
        companion object {
            fun createInitial() = State("", 0L)
        }
    }

    sealed class Action {
        data object Idle : Action()
        data object AddPoints : Action()
        data object SubtractPoints : Action()
        data object GoBack : Action()
        data object DoSth : Action()
        data class ChangeUsername(val username: String) : Action()
    }

    sealed class Effect {
        data object GoBack : Effect()
    }
}