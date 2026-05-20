package com.dhruva.modernandroidarchitectures_mvi.feature.user_modified_mvi.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.dhruva.modernandroidarchitectures_mvi.feature.user_modified_mvi.base.ErrorState

@Composable
fun ErrorDialog(errorState: ErrorState) {
    Dialog(onDismissRequest = errorState.onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, MaterialTheme.shapes.large)
                .padding(16.dp),
        ) {
            Text(
                text = stringResource(id = errorState.title),
                fontWeight = FontWeight.Bold,
                fontSize = TextUnit(24f, TextUnitType.Sp)
            )
            Text(
                text = stringResource(id = errorState.description),
                modifier = Modifier.padding(top = 8.dp)
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(top = 24.dp)
            ) {
                Button(
                    onClick = errorState.onDismiss,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Dismiss")
                }

                OutlinedButton(
                    onClick = errorState.onTryAgain,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Try again")
                }
            }
        }
    }
}