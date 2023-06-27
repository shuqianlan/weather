package com.ilifesmart;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.ilifesmart.ui.Custom3DView;
import com.ilifesmart.weather.R;


public class Rotate3DActivity extends AppCompatActivity {

    Custom3DView mCamera;
    TextView mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotate3_d);
        mCamera = findViewById(R.id.camera);
        mText = findViewById(R.id.text);

        mCamera.removeAllViewsInLayout();
        for (int i = 0; i < 3; i++) {
            ImageView v = new ImageView(this);
            v.setBackgroundResource(R.mipmap.ic_launcher);
            mCamera.addView(v);
        }
    }

    public void onViewClicked() {

    }

    public static Intent newIntent(Context context) {
        return new Intent(context, Rotate3DActivity.class);
    }
}
