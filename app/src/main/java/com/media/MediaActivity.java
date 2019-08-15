package com.media;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.ilifesmart.utils.Utils;
import com.ilifesmart.weather.R;

public class MediaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.audio_mp3)
    public void onPlayMp3() {
        Utils.startActivity(this, MP3PlayerActivity.class);
    }

    @OnClick(R.id.audio_mp4)
    public void onPlayMp4() {
    }
}
