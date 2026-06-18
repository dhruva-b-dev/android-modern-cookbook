package com.dhruva.androidbackgroundwork

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast

enum class Buttons {
    IntentService, Service, BoundService, ForegroundService, WorkManager
}

fun getButtons(): List<Buttons> {
    return listOf(
        Buttons.IntentService, Buttons.Service, Buttons.BoundService, Buttons.ForegroundService,
        Buttons.WorkManager
    )
}

enum class BoundServiceButtons() {
    SayHello, GetRandomNumber, BindService, UnbindService
}

fun getBoundServiceButtons(): List<BoundServiceButtons> {
    return listOf(
        BoundServiceButtons.SayHello,
        BoundServiceButtons.GetRandomNumber,
        BoundServiceButtons.BindService,
        BoundServiceButtons.UnbindService
    )
}

enum class ForegroundServiceButtons() {
    StartForegroundService, StopForegroundService, BindService, UnbindService, SayHello
}

fun getForegroundServiceButtons(): List<ForegroundServiceButtons> {
    return listOf(
        ForegroundServiceButtons.StartForegroundService,
        ForegroundServiceButtons.StopForegroundService,
        ForegroundServiceButtons.BindService,
        ForegroundServiceButtons.UnbindService,
        ForegroundServiceButtons.SayHello
    )
}


enum class WorkManagerButtons {
    StartWorkManager, ScheduleWorkManager, StopWorkManager
}

fun getWorkManagerButtons(): List<WorkManagerButtons> {
    return listOf(
        WorkManagerButtons.StartWorkManager,
        WorkManagerButtons.ScheduleWorkManager,
        WorkManagerButtons.StopWorkManager
    )
}
//Toast extension function
fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

//Logger extension function
fun Any.logDebug(message: String) {
    Log.d(this::class.java.simpleName,message)
}

@SuppressLint("DefaultLocale")
fun Long.toFormattedTime(): String {
    val hours = this / 3600000
    val minutes = (this % 3600000) / 60000
    val seconds = (this % 60000) / 1000
    val milliseconds = this % 1000
    return String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, milliseconds)
}
