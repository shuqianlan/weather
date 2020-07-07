package com.amap;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.ilifesmart.weather.R;

public class AmapActivity extends AppCompatActivity implements AMapLocationListener {

    public AMapLocationClient mLocationClient = null;
    public static final String TAG = "Amap";
    
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                Log.d(TAG, "onLocationChanged: Latitude " + aMapLocation.getLatitude());
                Log.d(TAG, "onLocationChanged: Longitude " + aMapLocation.getLongitude());
                Log.d(TAG, "onLocationChanged: Address " + aMapLocation.getAddress());

            } else {
                Log.d(TAG, "onLocationChanged: Error " + aMapLocation.getErrorCode() + " Error " + aMapLocation.getErrorInfo());
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amap);

        mLocationClient = new AMapLocationClient(getApplicationContext());
        mLocationClient.setLocationListener(this);

        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        option.setOnceLocation(true);
        option.setOnceLocationLatest(true); // 获取3秒内精度最高的一次定位
        option.setInterval(1000); // 设置间隔，默认为2s
        option.setNeedAddress(true);
        option.setLocationCacheEnable(false);
        
        if (mLocationClient != null) {
            mLocationClient.setLocationOption(option);
            mLocationClient.stopLocation();

            mLocationClient.startLocation();
        }

    }

}