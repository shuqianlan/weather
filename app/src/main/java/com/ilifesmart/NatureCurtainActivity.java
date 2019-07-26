package com.ilifesmart;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;

import com.ilifesmart.ui.VerticalCurtain;
import com.ilifesmart.weather.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NatureCurtainActivity extends AppCompatActivity {

	@BindView(R.id.seekbar)
	SeekBar mSeekbar;

	public static final String TAG = "CurtainActivity";
	@BindView(R.id.progress)
	TextView mProgress;
	@BindView(R.id.screen)
	VerticalCurtain mScreen;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nature_curtain);
		ButterKnife.bind(this);

		mSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				Log.d(TAG, "onProgressChanged: progress " + progress);
				mProgress.setText(progress + "%");
				mScreen.setProgress(progress);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				Log.d(TAG, "onStartTrackingTouch: play " + seekBar.getProgress());
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				Log.d(TAG, "onStopTrackingTouch: end " + seekBar.getProgress());
			}
		});


	}

}
