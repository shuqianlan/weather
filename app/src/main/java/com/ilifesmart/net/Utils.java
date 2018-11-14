package com.ilifesmart.net;

import android.net.Uri;

import com.ilifesmart.interfaces.Response;

import java.io.IOException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class Utils {
    private static String WEATHER_CAIYUN_PRE_URL = "https://api.caiyunapp.com/v2/";
    private static String WEATHER_CAIYUN_KEY = "5Jn=rqANZl-i590W";
    private static OkHttpClient sClient;


    public static String getRealTimeWeatherUrl(double longitude, double latitude) {
        return new StringBuilder().append(WEATHER_CAIYUN_PRE_URL)
                .append(WEATHER_CAIYUN_KEY).append("/")
                .append(longitude).append(",").append(latitude).append("/")
                .append("realtime.json")
                .toString()
                .trim();
    }

    public static String getForecastWeatherUrl(double longitude, double latitude) {
        return new StringBuilder().append(WEATHER_CAIYUN_PRE_URL)
                .append(longitude).append(",").append(latitude).append("/")
                .append("forecast.json")
                .toString()
                .trim();
    }




    private static OkHttpClient getOKHttpClientInstance() {
        if (sClient == null) {
            synchronized (Utils.class) {
                if (sClient == null) {
                    sClient = new OkHttpClient();
                }
            }
        }

        return sClient;
    }

    public static void netCallGet(String url, Response cb) {
        OkHttpClient client = getOKHttpClientInstance();
        Request request = new Request.Builder().get().url(url).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (cb != null) {
                    cb.onFailure(e);
                }
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                String content = response.body().string();
                if (cb != null) {
                    cb.onSuccess(content);
                }
            }
        });
    }

}
