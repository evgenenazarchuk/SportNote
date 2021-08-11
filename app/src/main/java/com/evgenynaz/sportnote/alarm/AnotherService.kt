package com.evgenynaz.myhomework.alarm

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.widget.Toast


class AnotherService : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        //Toast
        Toast.makeText(applicationContext, "Hello", Toast.LENGTH_LONG ).show()

        return  START_NOT_STICKY
    }

}