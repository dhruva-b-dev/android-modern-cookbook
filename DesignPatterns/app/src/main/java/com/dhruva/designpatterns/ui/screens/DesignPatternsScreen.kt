package com.dhruva.designpatterns.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dhruva.designpatterns.ui.components.ButtonComponent
import com.dhruva.designpatterns.ui.theme.DesignPatternsTheme

@Composable
fun DesignPatternsScreen(
    modifier: Modifier = Modifier,
    navigateToCreationalScreen: () -> Unit,
    navigateToStructuralScreen: () -> Unit,
    navigateToBehaviouralScreen: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ButtonComponent(
            onClick = {
                //navigate to creational patterns screen
                navigateToCreationalScreen()
            },
            buttonText = "Creational"
        )

        ButtonComponent(
            onClick = {
                ////navigate to behavioral patterns screen
                navigateToBehaviouralScreen()
            },
            buttonText = "Behavioural"
        )

        ButtonComponent(
            onClick = {
                //navigate to structural patterns screen
                navigateToStructuralScreen()
            },
            buttonText = "Structural"
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DesignPatternsScreenPreview() {
    DesignPatternsTheme {
        DesignPatternsScreen(
            navigateToCreationalScreen = {},
            navigateToStructuralScreen = {},
            navigateToBehaviouralScreen = {},
        )
    }
}
