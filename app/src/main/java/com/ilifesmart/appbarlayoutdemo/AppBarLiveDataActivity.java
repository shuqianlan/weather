package com.ilifesmart.appbarlayoutdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.ilifesmart.weather.R;
import com.ilifesmart.weather.databinding.AppbarLivedataLayoutBinding;

public class AppBarLiveDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppbarLivedataLayoutBinding binder = DataBindingUtil.setContentView(this, R.layout.appbar_livedata_layout);
        binder.setActivity(this);
    }

    public void onRefreshView(View view) {
        Log.d("{BBBB", "onRefreshView: ");
    }
}
