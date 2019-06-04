package com.ilifesmart.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.ilifesmart.utils.DensityUtils;
import com.ilifesmart.weather.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SurfaceVideoView extends FrameLayout implements MediaPlayer.OnPreparedListener, SurfaceHolder.Callback {

	public static final String TAG = "VideoArea";

	private MediaPlayer player;
	private Thread mThread;
	private boolean isRunning = true;
	private String filePath;
	private int mWindWidth;
	private int mWindHeight;
	private boolean isPrepared;

	@BindView(R.id.video)
	SurfaceView mVideo;
	@BindView(R.id.barrage)
	BarrageView mBarrage;
	@BindView(R.id.video_cont)
	FrameLayout mVideoCont;
	@BindView(R.id.time)
	TextView mTime;
	@BindView(R.id.timeline)
	SeekBar mTimeline;
	@BindView(R.id.duration)
	TextView mDuration;
	@BindView(R.id.state)
	ImageView mState;
	@BindView(R.id.control_area)
	ConstraintLayout mControlArea;

	public SurfaceVideoView(Context context) {
		this(context, null);
	}

	public SurfaceVideoView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SurfaceVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		setKeepScreenOn(true);

		View v = LayoutInflater.from(context).inflate(R.layout.layout_surface_video_view, this);
		ButterKnife.bind(this, v);

		initialize(context);
	}

	private void initialize(Context context) {
		SurfaceHolder holder = mVideo.getHolder();
		holder.addCallback(this);

		int[] array = new int[2];
		DensityUtils.getWindowSize(context, array);
		mWindWidth = array[0];
		mWindHeight = array[1];

		mTimeline.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				player.seekTo(seekBar.getProgress());
			}
		});

		mThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (isRunning) {
					if (player != null && player.isPlaying()) {
						mVideoCont.post(new Runnable() {
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
					try {
						Thread.sleep(100);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
				onClose();
			}
		});
		resetMediaPlayer();
	}

	private void onClose() {
		if (player != null) {
			player.release();
			player = null;
		}
		setKeepScreenOn(false);
	}

	private String getDurationString(int time) {
		time /= 1000;
		int mins = time / 60;
		int secs = time % 60;
		int hours = time / 3600;
		String title = String.format("%02d:%02d:%02d", hours, mins, secs);

		return title;
	}

	@TargetApi(Build.VERSION_CODES.N)
	private void resetMediaPlayer() {
		player = new MediaPlayer();
		player.setOnPreparedListener(this);

		player.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
			@Override
			public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
				setFrameContSize(width, height);
			}
		});
	}

	private void checkVideoExists() {
		if (TextUtils.isEmpty(filePath)) {
			throw new IllegalArgumentException("invalid file path.");
		}
	}

	private void checkVideoPrepared() {
		if (!isPrepared) {
			throw new IllegalArgumentException("MediaPlayer not yet prepared.");
		}
	}

	@TargetApi(Build.VERSION_CODES.N)
	public void setVideoPath(String filePath) {
		player.reset();
		isPrepared = false;

		this.filePath = filePath;
		checkVideoExists();
		try {
			AssetFileDescriptor descriptor = getResources().getAssets().openFd(filePath);
			player.setDataSource(descriptor);
			player.prepareAsync();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void setFrameContSize(int width, int height) {
		Integer screenWidth = mWindWidth;
		Integer screenHeight = mWindHeight;
		FrameLayout.LayoutParams videoParams = (FrameLayout.LayoutParams)mVideoCont.getLayoutParams();

		if (width > height) {
			videoParams.width = screenWidth;
			videoParams.height = screenWidth * height / width;
		} else {
			videoParams.width = screenHeight * width / height;
			videoParams.height = screenHeight;
		}

		mVideoCont.setLayoutParams(videoParams);

		FrameLayout.LayoutParams lp1 = (FrameLayout.LayoutParams) mVideo.getLayoutParams();
		lp1.width = mWindWidth;
		lp1.height = videoParams.height;

		mVideo.setLayoutParams(lp1);
		mBarrage.setLayoutParams(lp1);
	}

	@OnClick(R.id.control_area)
	public void onControlAreaClicked() {
		checkVideoPrepared();

		mState.setSelected(!mState.isSelected());
		if (player != null) {
			if (mState.isSelected()) {
				if (!player.isPlaying()) {
					player.start();
				}
			} else {
				if (player.isPlaying()) {
					player.pause();
				}
			}
		}
	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		isPrepared = true;
		player.setLooping(true);
		mThread.start();
		mTimeline.setMax(player.getDuration());
		mTime.setText(getDurationString(0));
		mDuration.setText(getDurationString(player.getDuration()));
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		player.setDisplay(holder);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) { }

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) { }

	public void onDestroyed() {
		isRunning = false;
	}

	public void sendBarrage() {
		mBarrage.sendBarrage();
	}

	public void sendBarrage(String barrage) {
		if (!TextUtils.isEmpty(barrage)) {
			mBarrage.sendBarrage(barrage);
		}
	}
}
