package com.ilifesmart.weather

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log

class SuperNotificationListenerService : NotificationListenerService() {
	
	companion object {
		val TAG = SuperNotificationListenerService::class.java.simpleName
	}
	
	override fun onListenerConnected() {
		super.onListenerConnected()
		
		Log.d(TAG, "onListenerConnected: ")
	}
	
	override fun onListenerDisconnected() {
		super.onListenerDisconnected()
		Log.d(TAG, "onListenerDisconnected: ")
	}
	
	override fun onNotificationPosted(sbn: StatusBarNotification?) {
		super.onNotificationPosted(sbn)
		
		Log.d(TAG, "onNotificationPosted: packageName ${sbn?.packageName}")
		Log.d(TAG, "onNotificationPosted: Notification.tickerText ${sbn?.notification?.tickerText}")
		Log.d(TAG, "onNotificationPosted: Notification.text ${sbn?.notification?.extras?.getString("text")}")
		Log.d(TAG, "onNotificationPosted: Notification.title ${sbn?.notification?.extras?.getString("title")}")
	}
	
	override fun onNotificationRemoved(sbn: StatusBarNotification?) {
		super.onNotificationRemoved(sbn)
		
		Log.d(TAG, "onNotificationRemoved: packageName ${sbn?.packageName}")
	}
}
