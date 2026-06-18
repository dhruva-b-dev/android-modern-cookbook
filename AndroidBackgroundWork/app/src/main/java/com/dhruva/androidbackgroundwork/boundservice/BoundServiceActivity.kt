package com.dhruva.androidbackgroundwork.boundservice

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dhruva.androidbackgroundwork.BoundServiceButtons
import com.dhruva.androidbackgroundwork.Buttons
import com.dhruva.androidbackgroundwork.R
import com.dhruva.androidbackgroundwork.getBoundServiceButtons
import com.dhruva.androidbackgroundwork.showToast
import com.dhruva.androidbackgroundwork.ui.theme.AndroidBackgroundWorkTheme

class BoundServiceActivity : AppCompatActivity() {
    private var boundService: BoundService? = null
    private var isBound = false

    private val serviceConnection by lazy {
        object : ServiceConnection {
            override fun onServiceConnected(
                name: ComponentName?,
                service: IBinder?
            ) {
                val binder = service as BoundService.LocalBinder
                boundService = binder.getService()
                isBound = true
                showToast("bound to BoundService")
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                boundService = null
                isBound = false
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AndroidBackgroundWorkTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BoundServiceScreen {
                        when (it) {
                            BoundServiceButtons.SayHello -> {
                                boundService?.sayHello()
                            }

                            BoundServiceButtons.GetRandomNumber -> {
                                boundService?.sayRandomNumber()
                            }

                            BoundServiceButtons.BindService -> {
                                val intent = Intent(this, BoundService::class.java)
                                bindService(intent, serviceConnection, BIND_AUTO_CREATE)
                            }

                            BoundServiceButtons.UnbindService -> {
                                if (isBound) {
                                    unbindService(serviceConnection)
                                    isBound = false
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun BoundServiceScreen(onClick: (type: BoundServiceButtons) -> Unit) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            for (button in getBoundServiceButtons()) {
                Button(
                    modifier = Modifier
                        .width(200.dp)
                        .height(48.dp),
                    onClick = { onClick(button) }) {
                    Text(text = button.name)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isBound) {
            unbindService(serviceConnection)
            isBound = false
        }
    }
}