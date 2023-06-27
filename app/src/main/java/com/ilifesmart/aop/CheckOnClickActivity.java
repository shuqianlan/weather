package com.ilifesmart.aop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ilifesmart.weather.R;

public class CheckOnClickActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = CheckOnClickActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_on_click);



//        mBtClickTest.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: ------------ ");
        setText();
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, CheckOnClickActivity.class);
    }

    public String setText() {
        return "XXXX";
    }
}
