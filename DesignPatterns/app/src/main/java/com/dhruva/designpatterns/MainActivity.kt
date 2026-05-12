package com.dhruva.designpatterns

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.dhruva.designpatterns.ui.navigation.AppNavigator
import com.dhruva.designpatterns.ui.theme.DesignPatternsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DesignPatternsTheme {
                AppNavigator()
            }
        }
    }
}



