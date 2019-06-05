package com.ilifesmart.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
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
import com.ilifesmart.utils.Utils;
import com.ilifesmart.weather.R;

import java.io.File;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SurfaceVideoView extends FrameLayout implements MediaPlayer.OnPreparedListener, SurfaceHolder.Callback {

	public static final String TAG = "VideoArea";
	private final float VISIBLE_MIN_ALPHA = 0.1f;
	private final float VISIBLE_MAX_ALPHA = 0.9f;
	private final int   VISIBLE_CONTROL_AREA_DURATION = 3000;
	private final int   CONTROL_AREA_DISAPPEAR_DURATION = 200;

	private MediaPlayer player;
	private Thread mThread;
	private boolean isRunning = true;
	private String filePath;
	private int mWindWidth;
	private int mWindHeight;
	private boolean isPrepared;
	private String copyPath;

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
	@BindView(R.id.thumbnail)
	ImageView mThumbNail;

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
								if (mThumbNail.getVisibility() != View.GONE) {
									mThumbNail.setVisibility(GONE);
								}

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

	private void autoSetVideoThumbnail() {
		AsyncTask.THREAD_POOL_EXECUTOR.execute(new Runnable() {
			@Override
			public void run() {
				try {
					InputStream in = getResources().getAssets().open(filePath);
					copyPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES).getAbsolutePath() + File.separator + "video.mp4";
					Bitmap mp = Utils.getVideoThumbnail(in, copyPath);
					in.close();

					mThumbNail.post(()->{
										mThumbNail.setImageBitmap(mp);
									}
					);
				} catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		});

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
	public void setVideoPath(String videoPath) {
		AsyncTask.THREAD_POOL_EXECUTOR.execute(new Runnable() {
			@Override
			public void run() {
				player.reset();
				isPrepared = false;

				filePath = videoPath;

				checkVideoExists();
				autoSetVideoThumbnail();
				try {
					AssetFileDescriptor descriptor = getResources().getAssets().openFd(videoPath);
					player.setDataSource(descriptor);
					player.prepareAsync();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
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
		if (mControlArea.getAlpha() < VISIBLE_MIN_ALPHA) {
			mControlArea.setAlpha(1);
			mControlArea.animate().cancel();
		}

		mControlArea.animate().setStartDelay(VISIBLE_CONTROL_AREA_DURATION).setDuration(CONTROL_AREA_DISAPPEAR_DURATION).alpha(0);
	}
	
	@OnClick(R.id.state)
	public void OnStatus() {
		if (mControlArea.getAlpha() > VISIBLE_MAX_ALPHA) {
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

		onControlAreaClicked();
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

		AsyncTask.THREAD_POOL_EXECUTOR.execute(new Runnable() {
			@Override
			public void run() {
				if (!TextUtils.isEmpty(copyPath)) {
					File file = new File(copyPath);
					if (file != null && file.isFile()) {
						file.delete();
					}
				}
			}
		});
	}

	public void sendBarrage() {
		mBarrage.sendBarrage();
	}

	public void sendBarrage(String barrage) {
		if (!TextUtils.isEmpty(barrage)) {
			mBarrage.sendBarrage(barrage);
		}
	}

	@RequiresApi(api = Build.VERSION_CODES.M)
	public void setVideoSpeed(float speed) {
		if (player != null) {
			player.setPlaybackParams(player.getPlaybackParams().setSpeed(speed));
		}
	}

	public void setBarrageClosed(boolean closed) {
		mBarrage.setBarrageClosed(closed);
	}
}
