package com.dhruva.androidbackgroundwork.intentservice

import android.app.IntentService
import android.content.Intent
import com.dhruva.androidbackgroundwork.logDebug
import com.dhruva.androidbackgroundwork.showToast

class MyIntentService  : IntentService("MyIntentService") {
    @Deprecated("Deprecated in Java")
    override fun onHandleIntent(intent: Intent?) {
        logDebug("onHandleIntent: Thread name: ${Thread.currentThread().name}")

        try {
            //to stimulate long-running task
            Thread.sleep(5000)
        }catch (e : InterruptedException){
            e.printStackTrace()
        }

        showToast("Service executed")
        logDebug("Service executed")
    }
}