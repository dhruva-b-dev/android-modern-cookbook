package com.dhruva.modernandroidarchitectures_mvi.feature

/*
* The Intent sealed interface contains all the possible user interactions
* and system events that can trigger state changes
* */
sealed interface TodoIntent {
    data object LoadItems : TodoIntent
    data class AddItem(val value: String) : TodoIntent
    data class DeleteItem(val id: String) : TodoIntent
}