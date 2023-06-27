package com.ilifesmart;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.ilifesmart.weather.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

public class WifiInfoActivity extends AppCompatActivity {

    public static final String TAG = WifiInfoActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_wifi_info);
    }

    private WifiManager get() {
        return (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    }

    private ConnectivityManager getConnectivityManager() {
        return (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public void onClickGetNetInfo(View v) {
        WifiInfo info = get().getConnectionInfo();
        Log.d(TAG, "onClickGetNetInfo: info " + info);

        NetworkInfo netInfo = getConnectivityManager().getActiveNetworkInfo();
        Log.d(TAG, "onClickGetNetInfo: netInfo " + netInfo);
    }

    public void startScanInfo(View v) {

    }

    public void stopScanInfo(View v) {

    }

}