package com.dhruva.modernandroidarchitectures_mvi.feature.user_modified_mvi.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import com.dhruva.modernandroidarchitectures_mvi.feature.user_modified_mvi.component.ErrorDialog
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun <State, Effect, Contract : BaseScreenContract<State, Effect>> BaseScreen(
    contract: Contract,
    onEffect: (Effect) -> Unit,
    content: @Composable (state: State) -> Unit
) {
    val state = contract.viewState.collectAsState()

    content(state.value)

    val error = contract.errorState.collectAsState().value
    if (error != null) {
        when (error.type) {
            ErrorDialogType.COMMON -> ErrorDialog(error)
        }
    }

    LaunchedEffect(Unit) {
        contract.effect.onEach(onEffect).launchIn(this)
    }
}
