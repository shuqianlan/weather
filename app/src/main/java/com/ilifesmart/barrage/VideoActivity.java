package com.ilifesmart.barrage;

import android.annotation.TargetApi;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.ilifesmart.ui.BarrageView;
import com.ilifesmart.utils.DensityUtils;
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
	ConstraintLayout mFrameCont;
	//	@BindView(R.id.control)
	LinearLayout mControl;
	@BindView(R.id.time)
	TextView mTime;
	@BindView(R.id.timeline)
	SeekBar mTimeline;
	@BindView(R.id.duration)
	TextView mDuration;

	private MediaPlayer player;
	private int mWindWidth;
	private int mWindHeight;
	private Thread mThread;
	private boolean isRunning = true;
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

		int[] array = new int[2];
		DensityUtils.getWindowSize(this, array);
		mWindWidth = array[0];
		mWindHeight = array[1];
		player.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
			@Override
			public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
				setFrameContSize(width, height);
			}
		});
		try {
			AssetFileDescriptor descriptor = getResources().getAssets().openFd("video/Rise_of_Ascended.mp4");
			player.setDataSource(descriptor);
			player.prepareAsync();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		mTimeline.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				Log.d("BBBB", "onProgressChanged: progress " + progress);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				player.seekTo(seekBar.getProgress());
			}
		});

//		Observable.interval(100, TimeUnit.MILLISECONDS)
//						.observeOn(AndroidSchedulers.mainThread())
//						.subscribeOn(Schedulers.io())
//						.doOnNext(new Consumer<Long>() {
//							@Override
//							public void accept(Long aLong) throws Exception {
//
//							}
//						})
//						.subscribe(new Consumer<Long>() {
//							@Override
//							public void accept(Long aLong) throws Exception {
//
//							}
//						});
		mThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (isRunning) {
					synchronized (player) {
						if (player != null && player.isPlaying()) {
							mFrameCont.post(new Runnable() {
								@Override
								public void run() {
									int pastedTime = player.getCurrentPosition();
									int duration = player.getDuration();

									mTime.setText(getDurationString(pastedTime));
									mTimeline.setProgress(pastedTime);
									mDuration.setText(getDurationString(duration));
								}
							});

						}

					}
					try {
						Thread.sleep(100);
					} catch(Exception ex) {
						ex.printStackTrace();
					}
				}
				onClose();
			}
		});

	}

	private String getDurationString(int time) {
		time /= 1000;
		int mins = time/60;
		int secs = time%60;
		int hours= time/3600;
		String title = String.format("%02d:%02d", mins, secs);
		if (hours != 0) {
			title = String.format("%02d:%02d:%02d", hours, mins, secs);
		}

		return title;
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
		Integer screenWidth = mWindWidth;
		Integer screenHeight = mWindHeight;
		ViewGroup.LayoutParams videoParams = mFrameCont.getLayoutParams();

		if (width > height) {
			videoParams.width = screenWidth;
			videoParams.height = screenWidth * height / width;
		} else {
			videoParams.width = screenHeight * width / height;
			videoParams.height = screenHeight;
		}

		mFrameCont.setLayoutParams(videoParams);

		ConstraintLayout.LayoutParams lp1 = (ConstraintLayout.LayoutParams) mVideo.getLayoutParams();
		lp1.width = mWindWidth;
		lp1.height = videoParams.height;

		mVideo.setLayoutParams(lp1);
		mBarrage.setLayoutParams(lp1);

//		FrameLayout.LayoutParams lp2 = (FrameLayout.LayoutParams) mControl.getLayoutParams();
//		lp2.width = mWindWidth;
//		lp2.height = (int)(videoParams.height*0.2);
//
//		mControl.setLayoutParams(lp2);
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		player.setLooping(true);
		mThread.start();
		mTimeline.setMax(player.getDuration());
		mTime.setText(getDurationString(0));
		mDuration.setText(getDurationString(player.getDuration()));
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		isRunning = false;
	}

	private void onClose() {
		onPauseClicked();
		if (player != null && player.isPlaying()) {
			player.release();
			player = null;
		}
	}

	public class SurfaceCallBack implements SurfaceHolder.Callback {
		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			player.setDisplay(holder);
		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
		}
	}
}
