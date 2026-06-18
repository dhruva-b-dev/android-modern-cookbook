package com.dhruva.androidbackgroundwork.foregroundservice

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dhruva.androidbackgroundwork.ForegroundServiceButtons
import com.dhruva.androidbackgroundwork.getForegroundServiceButtons
import com.dhruva.androidbackgroundwork.logDebug
import com.dhruva.androidbackgroundwork.showToast
import com.dhruva.androidbackgroundwork.ui.theme.AndroidBackgroundWorkTheme

class ForegroundServiceActivity : AppCompatActivity(), ForegroundService.ServiceCallback {

    private var foregroundService: ForegroundService? = null
    private var isBound = false

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as ForegroundService.LocalBinder
            foregroundService = binder.getService()
            foregroundService?.setServiceCallback(this@ForegroundServiceActivity)
            isBound = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            isBound = false
        }
    }

    private val serviceIntent by lazy { Intent(this@ForegroundServiceActivity, ForegroundService::class.java) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidBackgroundWorkTheme() {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    ForegroundServiceScreen {
                        when (it) {
                            ForegroundServiceButtons.StartForegroundService -> {
                                startService(serviceIntent)
                            }

                            ForegroundServiceButtons.StopForegroundService -> {
                                stopForegroundService()
                            }

                            ForegroundServiceButtons.BindService -> {
                                bindService(serviceIntent, connection, Context.BIND_AUTO_CREATE)
                            }

                            ForegroundServiceButtons.UnbindService -> {
                                if (isBound) {
                                    unbindService(connection)
                                    isBound = false
                                }
                            }

                            ForegroundServiceButtons.SayHello -> {
                                if (isBound) {
                                    foregroundService?.sayHello()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun ForegroundServiceScreen(onClick: (type: ForegroundServiceButtons) -> Unit) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = CenterHorizontally,
            verticalArrangement = spacedBy(16.dp)
        ) {
            for (
            button in getForegroundServiceButtons()
            ) {
                Button(
                    onClick = { onClick(button) },
                    modifier = Modifier.size(width = 200.dp, height = 48.dp)
                ) {
                    Text(text = button.name)
                }
            }
        }
    }

    override fun onDestroy() {
        unbindServiceSafely()
        super.onDestroy()
    }

    private fun unbindServiceSafely() {
        if (isBound) {
            unbindService(connection)
            isBound = false
            logDebug("Service unbound")
            showToast("Service unbound")
        }
    }

    private fun stopForegroundService() {
        val intent = Intent(this, ForegroundService::class.java).apply {
            action = ForegroundService.STOP_SERVICE
        }
        startService(intent)
    }

    override fun onServiceDestroyed() {
        unbindServiceSafely()
        foregroundService = null
    }
}