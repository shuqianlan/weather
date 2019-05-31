package com.ilifesmart.barrage;

import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;

import com.ilifesmart.ui.BarrageView;
import com.ilifesmart.weather.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BarrageActivity extends AppCompatActivity {

	@BindView(R.id.barrage)
	BarrageView mBarrage;
	@BindView(R.id.video)
	SurfaceView mVideo;

	//Docs: https://developer.android.google.cn/guide/topics/media-apps/working-with-a-media-session.html#init-session
	private MediaSessionCompat mediaSession;
	private PlaybackStateCompat.Builder stateBuilder; // 后面重复使用.
	private static final String MEDIA_LOG = "Media";

	private IntentFilter mIntentFilter = new IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY);
	private BecommingNoisyReceiver mReceiver = new BecommingNoisyReceiver();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_barrage);
		ButterKnife.bind(this);

		initialize();
	}

	private void initialize() {


		mediaSession = new MediaSessionCompat(this, MEDIA_LOG);
		mediaSession.setFlags(
			MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |     // media button states
			MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS  // cmd transport
		);

		mediaSession.setMediaButtonReceiver(null); // intent用来reStarting Session

		stateBuilder = new PlaybackStateCompat.Builder()
						.setActions(
							PlaybackStateCompat.ACTION_PLAY |  // state1
							PlaybackStateCompat.ACTION_PAUSE |
							PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
							PlaybackStateCompat.ACTION_SKIP_TO_NEXT |
							PlaybackStateCompat.ACTION_SEEK_TO); // state2
		mediaSession.setPlaybackState(stateBuilder.build());

		mediaSession.setCallback(new MediaSessionCallback() {
			@Override
			public boolean onMediaButtonEvent(Intent mediaButtonEvent) {
				mediaButtonEvent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
				return super.onMediaButtonEvent(mediaButtonEvent);
			}

			@Override
			public void onPlay() {

				registerReceiver(mReceiver, mIntentFilter);
			}

			@Override
			public void onPause() {
				unregisterReceiver(mReceiver);
			}
		});
		mediaSession.setActive(true);

		MediaControllerCompat mediaController = new MediaControllerCompat(this, mediaSession);
		MediaControllerCompat.setMediaController(this, mediaController);

	}

	@OnClick(R.id.send)
	public void onSendbarrage() {
		mBarrage.sendBarrage();
	}

	@OnClick(R.id.play)
	public void onPlayVideo() {
	}

	@OnClick(R.id.pause)
	public void onPauseVideo() {
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onResume() {
		super.onResume();
		// ACTION_AUDIO_BECOMING_NOISY : 声音即将变得嘈杂的广播通知.
		// 播放音乐前:requestAudioFocus,获取焦点才可播放音乐.

		setVolumeControlStream(AudioManager.STREAM_MUSIC); // 控制音量
	}
}
