package com.dhruva.kotlinflow.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun CommonButtonComponent(text: String = "", onClick: () -> Unit = {}) {

    Button(
        onClick = onClick,
        modifier = Modifier.padding(8.dp)
    ) {
        Text(
            text = text
        )
    }
}