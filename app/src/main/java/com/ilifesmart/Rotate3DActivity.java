package com.ilifesmart;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.ilifesmart.ui.Custom3DView;
import com.ilifesmart.weather.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Rotate3DActivity extends AppCompatActivity {

    @BindView(R.id.camera)
    Custom3DView mCamera;
    @BindView(R.id.text)
    TextView mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotate3_d);
        ButterKnife.bind(this);

        mCamera.removeAllViewsInLayout();
        for (int i = 0; i < 3; i++) {
            ImageView v = new ImageView(this);
            v.setBackgroundResource(R.mipmap.ic_launcher);
            mCamera.addView(v);
        }
    }

    @OnClick(R.id.text)
    public void onViewClicked() {

    }

    public static Intent newIntent(Context context) {
        return new Intent(context, Rotate3DActivity.class);
    }
}
