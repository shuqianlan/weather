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
import com.ilifesmart.weather.R

class HelloService : Service() {

    companion object {
        val EXTRA_KEY = "com.services.HelloService.MESSAGE_KEY"
        val EXTRA_FROUND = "com.services.HelloService.FROUND_NOTIFICATION"
        val EXTRA_AUTO_CLOSE = "com.services.HelloService.SERVICE_AUTO_CLOSE"

    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    private var serviceLooper: Looper? = null
    private var serviceHandler: ServiceHandler? = null

    private inner class ServiceHandler(looper: Looper): Handler(looper) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)

            try {
                Thread.sleep(5000)
            } catch (e:InterruptedException) {
                Thread.currentThread().interrupt()
            }

//            stopSelf()
        }
    }

    override fun onCreate() {
        super.onCreate()

        HandlerThread("ServicesStartArguments", Process.THREAD_PRIORITY_BACKGROUND).apply {
            start()

            serviceLooper = looper
            serviceHandler = ServiceHandler(looper)
        }
    }

    private val CHANNEL_ID = "HelloServiceSound"
    @TargetApi(Build.VERSION_CODES.O)
    private fun createFroundNotification() { //设定的通知渠道名称
        val channelName = "Notification Center"
        //设置通知的重要程度
        val importance = NotificationManager.IMPORTANCE_LOW
        //构建通知渠道
        val channel = NotificationChannel(
            CHANNEL_ID,
            channelName,
            importance
        )
        channel.description = "LifeSmart"
        //在创建的通知渠道上发送通知
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
        builder.setSmallIcon(R.drawable.ic_launcher_foreground) //设置通知图标
            .setContentTitle("LifeSmart") //设置通知标题
            .setContentText("通知中心") //设置通知内容
            .setOngoing(true) //设置处于运行状态
        //向系统注册通知渠道，注册后不能改变重要性以及其他通知行为
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
        //将服务置于启动状态 NOTIFICATION_ID指的是创建的通知的ID
        startForeground(0, builder.build())
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show()

        val autoClose = intent?.getBooleanExtra(EXTRA_AUTO_CLOSE, false) ?: false
        val text = intent?.getStringExtra(EXTRA_KEY) ?: "unknown"
        val notification = intent?.getBooleanExtra(EXTRA_FROUND, false) ?: false
        serviceHandler?.obtainMessage()?.also { message ->
//            message.arg1 = key
            serviceHandler?.sendMessage(message)
        }

        if (autoClose) {
            serviceHandler?.postDelayed(Runnable {
                stopSelf()
            }, 2000)
        }

//        if (notification) {
//            createFroundNotification()
//        } else {
//            stopForeground(true)
//        }
//        return START_STICKY
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        Log.d("HelloService", "onDestroy: HelloService closed")
        Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show()
        super.onDestroy()
    }
}
