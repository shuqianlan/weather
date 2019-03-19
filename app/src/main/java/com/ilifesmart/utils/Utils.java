package com.ilifesmart.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.ilifesmart.weather.R;

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

    public static Intent newIntent(Context context, Class<? extends Activity> cls) {
        return new Intent(context, cls);
    }

    public static void startActivity(Context context, Class<? extends Activity> cls) {
        context.startActivity(newIntent(context, cls));
    }

    public static AlertDialog creatLoadingDialog(Context context) {
        View v = LayoutInflater.from(context).inflate(R.layout.fragment_loading_dialog, null);
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(v)
                .create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }
}
