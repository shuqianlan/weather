package com.ilifesmart.weather;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.ilifesmart.WeatherApplication;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.internal.Utils;

public class HomeActivity extends AppCompatActivity {

    public static final String TAG = "HomeActivity";
    private static List<String> mPermissions = new ArrayList<>();
    public static final int REQUEST_PERMISSION_CODE = 100;

    static {
        mPermissions.add(Manifest.permission.READ_PHONE_STATE);
        mPermissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        mPermissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (int i = mPermissions.size() - 1; i >= 0; i--) {
                if (checkSelfPermission(mPermissions.get(i)) == PackageManager.PERMISSION_GRANTED) {
                    if (mPermissions.get(i).equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                        WeatherApplication.startLocation();
                    }
                    mPermissions.remove(i);
                }
            }

            if (mPermissions.size() > 0) {
                String[] permissions = mPermissions.toArray(new String[mPermissions.size()]);
                requestPermissions(permissions, REQUEST_PERMISSION_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISSION_CODE) {
            for (int index = 0; index < permissions.length; index++) {
                if (grantResults[index] == PackageManager.PERMISSION_GRANTED) {
                    if (permissions[index].equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                        WeatherApplication.startLocation();
                    }
                }
            }
        }
    }

    @OnClick(R.id.start)
    public void onViewClicked() {
        Log.d(TAG, "onViewClicked: realtime_url " + com.ilifesmart.net.Utils.getRealTimeWeatherUrl(120.2,30.3));
        Log.d(TAG, "onViewClicked: forecast_url " + com.ilifesmart.net.Utils.getForecastWeatherUrl(120.2,30.3));
    }
}
