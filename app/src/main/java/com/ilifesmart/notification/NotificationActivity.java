package com.ilifesmart.notification;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.ilifesmart.weather.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);
    }

    @TargetApi(Build.VERSION_CODES.O)
    @OnClick(R.id.send_notification_high)
    public void onSendHighNotification() {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new Notification.Builder(this, "chat")
                .setContentTitle("收到一条新消息")
                .setContentText("门已打开")
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .build();

        manager.notify(1, notification);
    }

    @TargetApi(Build.VERSION_CODES.O)
    @OnClick(R.id.send_notification_def)
    public void onSendDefNotification() {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new Notification.Builder(this, "speedy")
                .setContentTitle("收到一条新消息")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .build();

        manager.notify(2, notification);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, NotificationActivity.class);
    }


}
