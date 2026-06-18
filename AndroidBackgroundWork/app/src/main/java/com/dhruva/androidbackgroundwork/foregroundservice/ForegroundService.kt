package com.dhruva.androidbackgroundwork.foregroundservice

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.dhruva.androidbackgroundwork.logDebug
import com.dhruva.androidbackgroundwork.showToast
import com.dhruva.androidbackgroundwork.toFormattedTime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class ForegroundService : Service() {

    companion object {
        private const val CHANNEL_ID = "my_channel"
        private const val NOTIFICATION_ID = 1
        const val STOP_SERVICE = "STOP_SERVICE"
    }

    private var notificationManager: NotificationManager? = null
    private var coroutineJob: Job? = null
    private var binder = LocalBinder()
    private var isServiceStarted = false

    private var serviceCallback: ServiceCallback? = null

    interface ServiceCallback {
        fun onServiceDestroyed()
    }

    fun setServiceCallback(callback: ServiceCallback) {
        serviceCallback = callback
    }

    override fun onBind(intent: Intent?): IBinder {
        logDebug("onBind: Thread name: ${Thread.currentThread().name}")
        return binder
    }

    inner class LocalBinder : Binder() {
        fun getService(): ForegroundService = this@ForegroundService
    }

    @SuppressLint("ForegroundServiceType")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("ForegroundService", "onStartCommand called with action: ${intent?.action}, startId: $startId")

        if (intent?.action == STOP_SERVICE) {
            Log.d("ForegroundService", "Stop action received")
            stopForegroundService()
        } else if (!isServiceStarted) {
            Log.d("ForegroundService", "Starting service")
            isServiceStarted = true
            startForeground()
            startTimer()
        }

        return START_STICKY
    }

    private fun stopForegroundService() {
        Log.d("ForegroundService", "Stopping foreground service")
        isServiceStarted = false
        coroutineJob?.cancel()
        stopForeground(STOP_FOREGROUND_REMOVE)
        notificationManager?.cancel(NOTIFICATION_ID)
        serviceCallback?.onServiceDestroyed()
        stopSelf()
    }

    fun sayHello() {
        showToast("Hello from Foreground Service")
    }

    private fun startTimer() {
        coroutineJob?.cancel()
        coroutineJob = CoroutineScope(Dispatchers.Default).launch {
            var time = 0L
            while (isActive && isServiceStarted) {
                updateTimer(time.toFormattedTime())
                time += 1000
                delay(1000.milliseconds)
            }
        }
    }

    private fun updateTimer(time: String) {
        if (isServiceStarted) {
            val notification = baseNotification(time).build()
            notificationManager?.notify(NOTIFICATION_ID, notification)
        }
    }

    @SuppressLint("ForegroundServiceType")
    private fun startForeground() {
        try {
            notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    CHANNEL_ID,
                    "My Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                notificationManager?.createNotificationChannel(channel)
            }

            val notification = baseNotification("00:00:00.000").build()
            startForeground(NOTIFICATION_ID, notification)
        } catch (e: Exception) {
            Log.e("ForegroundService", "Error starting service: ${e.localizedMessage}")
        }
    }

    private fun getStopServiceIntent(): PendingIntent {
        val stopServiceIntent = Intent(this, this::class.java).apply {
            action = STOP_SERVICE
        }

        val pendingIntentFlag =
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT

        return PendingIntent.getService(this, 0, stopServiceIntent, pendingIntentFlag)
    }

    private fun baseNotification(time: String) = NotificationCompat.Builder(this, CHANNEL_ID)
        .setContentTitle("Foreground Service")
        .setContentText(time)
        .setSmallIcon(android.R.drawable.ic_dialog_info)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .addAction(
            android.R.drawable.ic_menu_close_clear_cancel,
            "Stop Service",
            getStopServiceIntent()
        )

    override fun onDestroy() {
        logDebug("onDestroy: Thread name: ${Thread.currentThread().name}")
        isServiceStarted = false
        coroutineJob?.cancel()
        super.onDestroy()
    }
}