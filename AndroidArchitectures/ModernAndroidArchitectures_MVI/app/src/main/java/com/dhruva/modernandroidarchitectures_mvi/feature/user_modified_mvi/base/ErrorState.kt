package com.dhruva.modernandroidarchitectures_mvi.feature.user_modified_mvi.base

import androidx.annotation.StringRes
import com.dhruva.modernandroidarchitectures_mvi.R
import kotlinx.coroutines.flow.MutableStateFlow

enum class ErrorDialogType { COMMON }

data class ErrorState(
    @StringRes val title: Int,
    @StringRes val description: Int,
    val onDismiss: () -> Unit,
    val onTryAgain: () -> Unit,
    val type: ErrorDialogType = ErrorDialogType.COMMON,
)

fun MutableStateFlow<ErrorState?>.dismiss() {
    value = null
}

fun MutableStateFlow<ErrorState?>.createErrorState(
    onDismiss: () -> Unit,
    onTryAgainAction: () -> Unit,
    @StringRes title: Int = R.string.error_common_title,
    @StringRes description: Int = R.string.error_common_description,
    type: ErrorDialogType = ErrorDialogType.COMMON,
) =
    if (this.value == null)
        ErrorState(title, description, onDismiss, onTryAgainAction, type)
    else
        this.value?.copy(onTryAgain = onTryAgainAction)