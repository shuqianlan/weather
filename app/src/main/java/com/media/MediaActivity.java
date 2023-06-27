package com.media;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import com.ilifesmart.utils.Utils;
import com.ilifesmart.weather.R;

public class MediaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);
    }

    public void onPlayMp3(View v) {
        Utils.startActivity(this, MP3PlayerActivity.class);
    }

    public void onPlayMp4(View v) {
    }
}
