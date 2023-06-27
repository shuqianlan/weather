package com.layout;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import com.ilifesmart.utils.Utils;
import com.ilifesmart.weather.R;

public class LayoutDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_demo);
    }

    public void onViewClicked(View v) {
        Utils.startActivity(this, LayoutDemo1Activity.class);
    }
}
