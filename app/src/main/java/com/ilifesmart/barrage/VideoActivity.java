package com.ilifesmart.barrage;

import android.annotation.TargetApi;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.FrameLayout;

import com.ilifesmart.ui.BarrageView;
import com.ilifesmart.weather.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VideoActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener {

	@BindView(R.id.video)
	SurfaceView mVideo;
	@BindView(R.id.barrage)
	BarrageView mBarrage;
	@BindView(R.id.frame_cont)
	FrameLayout mFrameCont;

	private MediaPlayer player;
	private String filePath = "file:///android_asset/video/Rise_of_Ascended.mp4";

	@TargetApi(Build.VERSION_CODES.N)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_barrage);
		ButterKnife.bind(this);

		SurfaceHolder holder = mVideo.getHolder();
		holder.addCallback(new SurfaceCallBack());

		player = new MediaPlayer();
		player.setOnPreparedListener(this);
		try {
			AssetFileDescriptor descriptor = getResources().getAssets().openFd("video/Rise_of_Ascended.mp4");
			player.setDataSource(descriptor);
//			player.setDataSource(filePath);
			player.prepareAsync();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@OnClick(R.id.play)
	public void onPlayClicked() {
		if (player != null && !player.isPlaying()) {
			player.start();
		}
	}

	@OnClick(R.id.send)
	public void onBarrageClicked() {
		mBarrage.sendBarrage();
	}

	@OnClick(R.id.pause)
	public void onPauseClicked() {
		if (player != null && player.isPlaying()) {
			player.pause();
		}
	}

	private void setFrameContSize(int width, int height) {
		Log.d("BBBB", "setFrameContSize: [width,height] " + width + ", " + height);
//		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)mFrameCont.getLayoutParams();
//		lp.height = (int)((lp.width)*1.0*height/width);
//		Log.d("BBBB", "setFrameContSize: height " + height);
//		mFrameCont.setLayoutParams(lp);
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		player.setLooping(true);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		if (player != null && player.isPlaying()) {
			player.release();
			player = null;
		}
	}

	public class SurfaceCallBack implements SurfaceHolder.Callback {
		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			player.setDisplay(holder);
			setFrameContSize(player.getVideoWidth(), player.getVideoHeight());
		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {

		}
	}
}
