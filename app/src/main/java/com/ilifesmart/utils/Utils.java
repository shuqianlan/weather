package com.ilifesmart.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

public class Utils {

    private static long lastClickTimeMills;
    private final static int SPACE_TIME = 300;

    public synchronized static boolean isDoubleClick() {
        long currentTime = System.currentTimeMillis();
        boolean isDoubleClick = false;

        if ((currentTime - lastClickTimeMills) < SPACE_TIME) {
            isDoubleClick = true;
        }

        lastClickTimeMills = currentTime;
        return isDoubleClick;
    }

    public static Intent newIntent(Context context, Class<? extends AppCompatActivity> cls) {
        return new Intent(context, cls);
    }
}
