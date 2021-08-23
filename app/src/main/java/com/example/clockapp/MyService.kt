package com.example.clockapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.os.SystemClock
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompatExtras
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import java.text.DateFormat
import java.util.*

class MyService: Service() {

    private val CHANNEL_ID = "ForegroundService Kotlin"
    companion object {
        fun startService(context: Context, message: String) {
            val startIntent = Intent(context, MyService::class.java)
            startIntent.putExtra("inputExtra", message)
            ContextCompat.startForegroundService(context, startIntent)
        }
        fun stopService(context: Context) {
            val stopIntent = Intent(context, MyService::class.java)
            context.stopService(stopIntent)
        }
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        //do heavy work on a background thread
        initClock()
        showNotification()


        //stopSelf();
        return START_STICKY
    }

    private fun initClock() {
        val currentDateTimeString = DateFormat.getDateTimeInstance().format(Date())
        Thread.sleep(1000)
        currentDateTimeString.toString()
    }


    private fun showNotification() {

        val currentDateTimeString = DateFormat.getDateTimeInstance().format(Date())

        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0, notificationIntent, 0
        )
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)

            .setContentText(currentDateTimeString)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentIntent(pendingIntent)
            .build()
        startForeground(1, notification)
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(CHANNEL_ID, "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT)
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}