package com.ilifesmart.notification;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import androidx.core.app.NotificationCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;

import com.ilifesmart.weather.R;

import java.lang.reflect.Method;

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
        try {
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            Notification notification = new Notification.Builder(this, "speedy")
                    .setContentTitle("收到一条新消息")
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setAutoCancel(true)
                    .build();

            manager.notify(2, notification);
        } catch(Exception ex) {
        	ex.printStackTrace();
        }
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, NotificationActivity.class);
    }

    @OnClick({R.id.open_notification, R.id.close_notification})
    void notificationControl(View view) {
        switch (view.getId()) {
            case R.id.open_notification:
                notificationRealController(true);
                break;
            case R.id.close_notification:
                notificationRealController(false);
                break;
        }
    }

    private void notificationRealController(boolean isOpen) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            boolean isGranted = NotificationManagerCompat.from(this).areNotificationsEnabled();
            Log.d("TAG", "notificationRealController: isGranted " + isGranted);
            isOpen = true;

            NotificationManager manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

            if (isOpen) {
                try {
                    @SuppressLint("SoonBlockedPrivateApi") Method method = manager.getClass().getDeclaredMethod("setNotificationListenerAccessGranted", ComponentName.class, boolean.class);
                    method.setAccessible(true);
//                    method.invoke(manager, new ComponentName())


                    Intent i = new Intent();
                    i.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
                    i.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
                    i.putExtra(Settings.EXTRA_CHANNEL_ID, getApplicationInfo().uid);
                    i.putExtra("app_package", getPackageName());
                    i.putExtra("app_uid", getApplicationInfo().uid);

                    startActivity(i);

                } catch(Exception ex) {
                	ex.printStackTrace();
                }
            }
        }
    }
}
