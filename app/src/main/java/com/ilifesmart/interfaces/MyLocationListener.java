package com.ilifesmart.interfaces;

import android.util.Log;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;

public class MyLocationListener extends BDAbstractLocationListener {
    public static final String TAG = "OnLocationChanged";

    private double mLatitude;
    private double mLongitude;
    private static String lat_longitude = "120.15,30.28";

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {

        int errCode = bdLocation.getLocType();
//        Log.d(TAG, "onReceiveLocation: location " + bdLocation);
//        Log.d(TAG, "onReceiveLocation: latitude " + bdLocation.getLatitude());
//        Log.d(TAG, "onReceiveLocation: longitude " + bdLocation.getLongitude());
//        Log.d(TAG, "onReceiveLocation: errCode " + errCode);
        if (errCode == 61) {
            if (Math.abs(mLatitude-bdLocation.getLatitude()) > 0.1) {
                mLatitude = bdLocation.getLatitude();
            }

            if (Math.abs(mLongitude-bdLocation.getLatitude()) > 0.1) {
                mLongitude = bdLocation.getLongitude();
            }
        } else {
//            Log.d(TAG, "onReceiveLocation: bdLocation " + bdLocation);
        }

    }
}
