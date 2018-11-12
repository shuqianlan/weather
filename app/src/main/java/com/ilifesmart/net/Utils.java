package com.ilifesmart.net;

import android.net.Uri;

import java.net.URL;

public class Utils {
    private static String WEATHER_CAIYUN_PRE_URL = "https://api.caiyunapp.com/v2/";
    private static String WEATHER_CAIYUN_KEY = "5Jn=rqANZl-i590W";

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

}
