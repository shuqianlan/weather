package com.ilifesmart;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ClipboardManager;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.util.Printer;
import android.widget.Toast;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
//import com.google.firebase.iid.FirebaseInstanceId;
//import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.ilifesmart.interfaces.MyLocationListener;
import com.ilifesmart.region.RegionMgr;
import com.imou.LeChengCameraWrapInfo;
import com.lechange.opensdk.api.LCOpenSDK_Api;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.statistics.common.DeviceConfig;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ToolsApplication extends Application { //} implements Configuration.Provider {

    public static final String TAG = "Application";
    private static LocationClient mLocationClient;
    private static LocationClientOption option = new LocationClientOption();
    private static MyLocationListener mLocationListener = new MyLocationListener();

    private static Context mContext;
//    public static RefWatcher getRefWatcher(Context context) {
//        return refWatcher;
//    }
//    private static RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();

//        UMConfigure.setLogEnabled(true);
//        UMConfigure.init(this, "5f06db16895cca282300009c", "weather", UMConfigure.DEVICE_TYPE_PHONE, null);
//        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
//
//        LCOpenSDK_Api.setHost(LeChengCameraWrapInfo.APPCNUrl);
//
//        FirebaseApp.initializeApp(this);
//        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
//
////        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
////            @Override
////            public void onSuccess(InstanceIdResult instanceIdResult) {
////                String fcmDeviceToken = instanceIdResult.getToken();
////                Log.i(TAG, "FirebaseInstanceId: token: " + fcmDeviceToken);
////            }
////        });
//
//        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
//            @Override
//            public void onComplete(@NonNull Task<String> task) {
//                if (!task.isSuccessful()) {
//                    Log.w(TAG, "Fetching FCM registration token failed", task.getException());
//                    return;
//                }
//
//                // Get new FCM registration token
//                String token = task.getResult();
//                Log.d(TAG, "onComplete: token::" + token);
//            }
//        });
//        Looper.getMainLooper().setMessageLogging(new Printer() {
//            @Override
//            public void println(String x) {
//                Log.d(TAG, "println: MessageLogging " + x);
//            }
//        });
//        Log.d(TAG, "onCreate: " + getTestDeviceInfo(getApplicationContext()));

//        String[] infos =  UMConfigure.getTestDeviceInfo(getApplicationContext());
//
//        for (int i = 0; i < infos.length; i++) {
//            Log.d(TAG, "onCreate: Info " + infos[i]);
//        }
        //        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//
//        ExcludedRefs excludedRefs = AndroidExcludedRefs.createAppDefaults()
//                .instanceField("android.view.inputmethod.InputMethodManager", "sInstance")
//                .instanceField("android.view.inputmethod.InputMethodManager", "mLastSrvView")
//                .instanceField("com.android.internal.policy.PhoneWindow$DecorView", "mContext")
//                .instanceField("android.support.v7.widget.SearchView$SearchAutoComplete", "mContext")
//                .build();
//        refWatcher = refWatcher(this).listenerServiceClass(DisplayLeakService.class)
//                .excludedRefs(excludedRefs)
//                .buildAndInstall();

        mContext = getApplicationContext();
        RegionMgr.getInstance().initialize(mContext);
        mLocationClient = new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(mLocationListener);

//        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
//        option.setCoorType("GCJ02"); // 经纬度坐标类型.
//        option.setScanSpan(1000); // 请求间隔
//        option.setOpenGps(true);
//        option.setLocationNotify(true);
//        option.setIgnoreKillProcess(false);
//        option.setWifiCacheTimeOut(5*60*1000);
//        option.setEnableSimulateGps(false);
//        mLocationClient.setLocOption(option);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            String channelID = "chat";
//            String channelName = "消息通知";
//            int importance = NotificationManager.IMPORTANCE_HIGH;
//            creatNotificationChannel(channelID, channelName, importance);
//
//            channelID = "speedy";
//            channelName = "便捷开关";
//            importance = NotificationManager.IMPORTANCE_DEFAULT;
//            creatNotificationChannel(channelID, channelName, importance);
//
//            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//            NotificationChannel channel = manager.getNotificationChannel("speedy");
//            if (channel.getImportance() == NotificationManager.IMPORTANCE_NONE) {
//                Log.d(TAG, "onCreate: -- ");
//            }
//        }
//
//        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
//            @Override
//            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
//
//            }
//
//            @Override
//            public void onActivityStarted(@NonNull Activity activity) {
//
//            }
//
//            @Override
//            public void onActivityResumed(@NonNull Activity activity) {
//                MobclickAgent.onResume(activity);
//
//            }
//
//            @Override
//            public void onActivityPaused(@NonNull Activity activity) {
//                MobclickAgent.onPause(activity);
//            }
//
//            @Override
//            public void onActivityStopped(@NonNull Activity activity) {
//
//            }
//
//            @Override
//            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
//
//            }
//
//            @Override
//            public void onActivityDestroyed(@NonNull Activity activity) {
//
//            }
//        });

    }

    public static String[] getTestDeviceInfo(Context context){
        String[] deviceInfo = new String[2];
        try {
            if(context != null){
                deviceInfo[0] = DeviceConfig.getDeviceIdForGeneral(context);
                deviceInfo[1] = DeviceConfig.getMac(context);
            }
        } catch (Exception e){
        }
        return deviceInfo;
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

// 自定义WorkManager的配置
//    @NonNull
//    @Override
//    public Configuration getWorkManagerConfiguration() {
//        return new Configuration.Builder()
//                .setMinimumLoggingLevel(Log.INFO)
//                .build();
//    }

}
