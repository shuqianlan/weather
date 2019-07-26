package com.ilifesmart.weather;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.ilifesmart.interfaces.Response;
import com.ilifesmart.net.Utils;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class WeatherActivity extends AppCompatActivity {

    public static final String TAG = "Weather";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_home);
        ButterKnife.bind(this);

        Log.d(TAG, "onViewClicked: realtime_url " + Utils.getRealTimeWeatherUrl(120.2, 30.3));
        Log.d(TAG, "onViewClicked: forecast_url " + Utils.getForecastWeatherUrl(120.2, 30.3));

    }

    public static Intent newIntent(Context context) {
        return new Intent(context, WeatherActivity.class);
    }

    @OnClick({R.id.bt_realtime, R.id.bt_forecast})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_realtime:
                onRealTime();
                break;
            case R.id.bt_forecast:
                onForecast();
                break;
        }
    }

    private void onRealTime() {
        String url = Utils.getRealTimeWeatherUrl(120.2, 30.3);
        Utils.netCallGet(url, new Response() {
            @Override
            public void onSuccess(String msg) {
                Log.d(TAG, "onSuccess: realtime " + msg);
            }
        });
    }

    private void onForecast() {
        String url = Utils.getForecastWeatherUrl(120.2, 30.3);
        Log.d(TAG, "onForecast: url " + url);
        Utils.netCallGet(url, new Response() {
            @Override
            public void onSuccess(String msg) {
                Log.d(TAG, "onSuccess: forecast " + msg);
            }
        });
    }

}
