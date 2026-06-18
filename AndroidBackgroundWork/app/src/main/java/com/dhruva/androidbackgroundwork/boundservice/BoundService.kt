package com.dhruva.androidbackgroundwork.boundservice

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.dhruva.androidbackgroundwork.showToast
import java.util.Random


class BoundService : Service() {

    private val binder = LocalBinder()

    private val mGenerator = Random()

    /** Method for clients.  */
    private val randomNumber: Int
        get() = mGenerator.nextInt(100)

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    fun sayHello() {
        showToast("Hello from bound service")
    }

    fun sayRandomNumber() {
        showToast("Random number: $randomNumber")
    }

    override fun onDestroy() {
        super.onDestroy()
        showToast("Bound service destroyed!")
    }

    inner class LocalBinder : Binder() {
        // Return this instance of LocalService so clients can call public methods.
        fun getService(): BoundService = this@BoundService
    }

}