package com.ilifesmart;

import android.annotation.TargetApi;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.ilifesmart.interfaces.MyLocationListener;
import com.ilifesmart.weather.R;

public class ToolsApplication extends Application {

    public static final String TAG = "Application";
    private static LocationClient mLocationClient;
    private static LocationClientOption option = new LocationClientOption();
    private static MyLocationListener mLocationListener = new MyLocationListener();

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(mLocationListener);

        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("GCJ02"); // 经纬度坐标类型.
        option.setScanSpan(1000); // 请求间隔
        option.setOpenGps(true);
        option.setLocationNotify(true);
        option.setIgnoreKillProcess(false);
        option.setWifiCacheTimeOut(5*60*1000);
        option.setEnableSimulateGps(false);
        mLocationClient.setLocOption(option);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelID = "chat";
            String channelName = "消息通知";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            creatNotificationChannel(channelID, channelName, importance);

            channelID = "speedy";
            channelName = "便捷开关";
            importance = NotificationManager.IMPORTANCE_DEFAULT;
            creatNotificationChannel(channelID, channelName, importance);
            
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            NotificationChannel channel = manager.getNotificationChannel("speedy");
            if (channel.getImportance() == NotificationManager.IMPORTANCE_NONE) {
                Log.d(TAG, "onCreate: -- ");
            }
        }

    }

    public static boolean isInLocating() {
        return mLocationClient.isStarted();
    }

    public static void reStartLocation() {
        mLocationClient.restart();
    }

    public static void startLocation() {
        mLocationClient.start();
    }

    public static void stopLocation() {
        mLocationClient.stop();
    }

    public static Context getContext() {
        return mContext;
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void creatNotificationChannel(String channelID, String channelName, int importance) {
        NotificationChannel channel = new NotificationChannel(channelID, channelName, importance);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        channel.setSound(null, null); //禁用铃声
        channel.enableLights(false);    // 禁用铃声
        channel.enableVibration(false); // 禁用震动
        manager.createNotificationChannel(channel);
    }

}