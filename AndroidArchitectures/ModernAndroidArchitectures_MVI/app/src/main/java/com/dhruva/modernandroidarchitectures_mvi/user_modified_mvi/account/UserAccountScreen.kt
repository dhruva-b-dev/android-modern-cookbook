package com.dhruva.modernandroidarchitectures_mvi.feature.user_modified_mvi.account

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dhruva.modernandroidarchitectures_mvi.feature.user_modified_mvi.base.BaseScreen

@Composable
fun UserAccountScreen(contract: UserAccountContract, goBack: () -> Unit) {
    BaseScreen(
        contract = contract,
        onEffect = { effect ->
            when (effect) {
                UserAccountContract.Effect.GoBack -> goBack()
            }
        },
    ) { state ->
        UserAccountComposable(
            state = state,
            addPoints = contract::addPoints,
            subtractPoints = contract::subtractPoints,
            changeUsername = contract::changeUsername,
            showError = contract::showError
        )
    }
}

@Composable
private fun UserAccountComposable(
    state: UserAccountContract.State,
    addPoints: () -> Unit,
    subtractPoints: () -> Unit,
    changeUsername: (String) -> Unit,
    showError: () -> Unit
) {
    Column {
        Column(
            verticalArrangement = Arrangement.spacedBy(32.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
        ) {
            Username(username = state.username, changeUsername = changeUsername)

            Points(points = state.points, addPoints = addPoints, subtractPoints = subtractPoints)

            Button(onClick = showError) {
                Text(text = "Show error")
            }
        }
    }
}

@Composable
private fun Username(username: String, changeUsername: (String) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(text = "Welcome, $username!")

        OutlinedTextField(
            value = username,
            onValueChange = changeUsername,
            label = { Text(text = "Username") },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun Points(points: Long, addPoints: () -> Unit, subtractPoints: () -> Unit) {
    Column {
        Text(text = "You've got $points SnowPoints!")

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
        ) {
            OutlinedIconButton(
                onClick = addPoints,
                shape = CircleShape,
                modifier = Modifier.size(48.dp)
            ) {
                Icon(imageVector = Icons.Outlined.Add, contentDescription = "Add points")
            }

            OutlinedIconButton(
                onClick = subtractPoints,
                shape = CircleShape,
                modifier = Modifier.size(48.dp)
            ) {
                Icon(imageVector = Icons.Outlined.Delete, contentDescription = "Subtract points")
            }
        }
    }
}

