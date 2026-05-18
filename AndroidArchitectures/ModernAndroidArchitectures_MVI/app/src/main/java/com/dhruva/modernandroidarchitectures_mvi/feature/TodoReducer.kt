package com.dhruva.modernandroidarchitectures_mvi.feature

import com.dhruva.modernandroidarchitectures_mvi.model.TodoItem
import java.util.UUID

/**
 * Base interface for MVI Reducer
 */
interface MviReducer<I, S> {
    fun reduce(currentState: S, intent: I): S
}

/*
* Reducer handles transformation from one state from one state to another based on an
* incoming Intent
* */

class TodoReducer : MviReducer<TodoIntent, TodoUiState> {

    override fun reduce(currentState: TodoUiState, intent: TodoIntent): TodoUiState {
        return when (intent) {
            is TodoIntent.LoadItems -> currentState.copy(isLoading = true)
            is TodoIntent.AddItem -> currentState.copy(
                items = currentState.items + TodoItem(
                    id = UUID.randomUUID().toString(),
                    text = intent.value
                )
            )

            is TodoIntent.DeleteItem -> currentState.copy(
                items = currentState.items.filterNot { it.id == intent.id }
            )
        }
    }
}
