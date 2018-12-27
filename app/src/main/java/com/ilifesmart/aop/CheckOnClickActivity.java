package com.ilifesmart.aop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ilifesmart.weather.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CheckOnClickActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = CheckOnClickActivity.class.getSimpleName();

    @BindView(R.id.bt_click_test)
    Button mBtClickTest;
    @BindView(R.id.textView)
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_on_click);
        ButterKnife.bind(this);

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
