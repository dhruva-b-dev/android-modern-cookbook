package com.dhruva.modernandroidarchitectures_mvi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.dhruva.modernandroidarchitectures_mvi.feature.user_modified_mvi.account.UserAccountScreen
import com.dhruva.modernandroidarchitectures_mvi.feature.user_modified_mvi.account.UserAccountViewModel
import com.dhruva.modernandroidarchitectures_mvi.ui.theme.ModernAndroidArchitecturesTheme
import kotlin.getValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ModernAndroidArchitecturesTheme {
                val viewModel: UserAccountViewModel by viewModels()
                UserAccountScreen(contract = viewModel, goBack = {})
            }
        }
    }
}
