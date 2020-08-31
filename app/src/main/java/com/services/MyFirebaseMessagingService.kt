package com.services

import android.annotation.TargetApi
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.*
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.ilifesmart.weather.R

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)

        Log.d("BBBB", "onNewToken: $p0")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
//        super.onMessageReceived(remoteMessage)

        Log.d("MyFirebaseMessaging", "onMessageReceived: $remoteMessage")

        Log.d("BBBB", "From: ${remoteMessage.from}")

        // Check if message contains a data payload.
        remoteMessage.data.isNotEmpty().let {
            Log.d("BBBB", "Message data payload: " + remoteMessage.data)

//            if (/* Check if data needs to be processed by long running job */ true) {
//                // For long-running tasks (10 seconds or more) use WorkManager.
//                scheduleJob()
//            } else {
//                // Handle message within 10 seconds
//                handleNow()
//            }
        }

        // Check if message contains a notification payload.
        remoteMessage.notification?.let {
            Log.d("BBBB", "Message Notification Body: ${it.body}")
        }

    }
}
