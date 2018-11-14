package com.ilifesmart;

import android.app.Application;
import android.content.Context;

import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.ilifesmart.interfaces.MyLocationListener;

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

}
