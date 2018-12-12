package com.ilifesmart.thread;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.ilifesmart.weather.R;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ThreadTestActivity extends AppCompatActivity {

    public Map<String, Date> dateMaps = Collections.synchronizedMap(new HashMap<String, Date>());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_test);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, ThreadTestActivity.class);
    }

    private volatile boolean isRunning = true;
    @Override
    protected void onResume() {
        super.onResume();

        new Thread(new Runnable() {
            @Override
            public void run() {
                new TestHolder().initialize();
            }
        }).start();
    }
}
