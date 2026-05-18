package com.dhruva.modernandroidarchitectures_mvi.feature

import com.dhruva.modernandroidarchitectures_mvi.model.TodoItem

/*
* The UiState data class holds all the information needed to render the screen.
* This ensures single source of truth. Any composable needs to observe this state only to render UI,
* to prevent any inconsistencies and bugs.
* */

data class TodoUiState (
    val isLoading : Boolean = false,
    val items : List<TodoItem> = emptyList(),
    val error :String? = null
)