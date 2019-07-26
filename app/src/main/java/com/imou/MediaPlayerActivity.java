package com.imou;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import butterknife.ButterKnife;
import com.ilifesmart.weather.R;
import com.imou.media.MediaPlayFragment;
import com.imou.media.MediaPlayOnlineFragment;

public class MediaPlayerActivity extends AppCompatActivity implements MediaPlayFragment.BackHandlerInterface {

    public static final String TAG = "MediaPlayerActivity";
    private ChannelInfo channelInfo;
    private MediaPlayFragment mMediaPlayFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);
        ButterKnife.bind(this);

        String uuid = getIntent().getStringExtra(Intent.EXTRA_TEXT);
        channelInfo = LeChengMomgr.getInstance().getChannelInfo(uuid);
        if (channelInfo == null) {
            Log.d(TAG, "onCreate: Illeged channelInfo");
            finish();
        }

        Log.d(TAG, "onCreate: channelInfo " + channelInfo);

        MediaPlayOnlineFragment mediaPlayFragment = new MediaPlayOnlineFragment();
        Bundle b = new Bundle();
        b.putString("RESID", uuid);
        mediaPlayFragment.setArguments(b);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_cont, mediaPlayFragment)
                .commitAllowingStateLoss();
    }

    @Override
    public void setSelectedFragment(MediaPlayFragment backHandledFragment) {
        this.mMediaPlayFragment = backHandledFragment;
    }

}
