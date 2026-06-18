package com.dhruva.androidbackgroundwork

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dhruva.androidbackgroundwork.Buttons.*
import com.dhruva.androidbackgroundwork.backgroundservice.BackgroundService
import com.dhruva.androidbackgroundwork.boundservice.BoundServiceActivity
import com.dhruva.androidbackgroundwork.intentservice.MyIntentService
import com.dhruva.androidbackgroundwork.ui.theme.AndroidBackgroundWorkTheme
import kotlin.jvm.java

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidBackgroundWorkTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeScreen { type ->
                        when (type) {
                            IntentService -> {
                                startService(Intent(this, MyIntentService::class.java))
                            }

                            Service -> {
                                startService(Intent(this, BackgroundService::class.java))
                            }
                            BoundService -> {
                                val boundService = Intent(this, BoundServiceActivity::class.java)
                                startActivity(boundService)
                            }
                            ForegroundService -> {
                                val foregroundService = Intent(this, BoundServiceActivity::class.java)
                                startActivity(foregroundService)
                            }
                            WorkManager -> {
                                val workManager = Intent(this, BoundServiceActivity::class.java)
                                startActivity(workManager)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HomeScreen(onClick: (type: Buttons) -> Unit) {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        for (
        button in getButtons()
        ) {
            Button(
                onClick = { onClick(button) },
                modifier = Modifier.size(width = 200.dp, height =48.dp)
            ) {
                Text(text = button.name)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidBackgroundWorkTheme {
        HomeScreen(
            onClick = {}
        )
    }
}