package com.dhruva.androidbackgroundwork.backgroundservice

import android.app.Service
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.IBinder
import android.os.Looper
import android.os.Message
import com.dhruva.androidbackgroundwork.logDebug
import com.dhruva.androidbackgroundwork.showToast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class BackgroundService : Service(){

    private var serviceLooper: Looper? = null
    private var serviceHandler: ServiceHandler? = null

    // Handler that receives messages from the thread
    private inner class ServiceHandler(looper: Looper) : Handler(looper){

        override fun handleMessage(msg: Message) {
            val bundle = msg.data
            val stringData : String = bundle.getString("background_key") ?: "Default"
            showToast(stringData)
        }
    }

    override fun onCreate() {
        // Start up the thread running the service.  Note that we create a
        // separate thread because the service normally runs in the process's
        // main thread, which we don't want to block.  We also make it
        // background priority so CPU-intensive work will not disrupt our UI.
        HandlerThread("ServiceStartArguments").apply{
            start()

            //get the looper and use it for our handler
            serviceLooper = looper
            serviceHandler = ServiceHandler(looper)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        showToast("service starting")
        logDebug("service starting")

        CoroutineScope(Dispatchers.Default).launch{
            for(i in 1..5){
                val bundle = Bundle()
                bundle.putString("background_key","$i seconds have passed")
                val msg = Message.obtain()
                msg.what = i
                msg.data = bundle
                delay(2000.milliseconds)
                serviceHandler?.sendMessage(msg)
            }
            stopSelf()
        }

        // If we get killed, after returning from here, restart
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        //because we're not binding anything
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        logDebug("service done")
    }

}