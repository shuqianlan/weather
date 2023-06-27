package com.media;

import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.ilifesmart.weather.R;

public class MP3PlayerActivity extends AppCompatActivity {

    PlayerView mPlayerView;
    SimpleExoPlayer player;

    public static final String MP4URL = "http://www.ilifesmart.com:8089/aiweb/aipage/video/quan/dongganyinlv.mp4";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mp3_player);
        mPlayerView = findViewById(R.id.player_view);

        player = ExoPlayerFactory.newSimpleInstance(this);
        mPlayerView.setPlayer(player);

        player.addListener(new PlayStateListener());

        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, getPackageName()));
        Uri uri = Uri.parse(MP4URL);
        MediaSource videoSource = new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
        player.prepare(videoSource);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.release();
    }
}
