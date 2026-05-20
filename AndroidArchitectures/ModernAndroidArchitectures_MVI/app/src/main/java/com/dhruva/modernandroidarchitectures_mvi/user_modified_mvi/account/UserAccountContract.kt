package com.dhruva.modernandroidarchitectures_mvi.feature.user_modified_mvi.account

import com.dhruva.modernandroidarchitectures_mvi.feature.user_modified_mvi.base.BaseScreenContract

interface UserAccountContract :
    BaseScreenContract<UserAccountContract.State, UserAccountContract.Effect> {
    data class State(val username: String, val points: Long) {
        companion object {
            fun createInitial() = State("", 0L)
        }
    }

    fun addPoints()
    fun subtractPoints()
    fun goBack()
    fun doSth()
    fun changeUsername(username: String)

    sealed class Effect {
        data object GoBack : Effect()
    }

    fun showError()
}