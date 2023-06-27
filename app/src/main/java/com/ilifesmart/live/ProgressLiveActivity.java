package com.ilifesmart.live;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

import com.ilifesmart.utils.Utils;
import com.ilifesmart.weather.R;

public class ProgressLiveActivity extends AppCompatActivity {

	Button mSinglepixel;
	Button mForegroundService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_progress_live);
		mSinglepixel = findViewById(R.id.singlepixel);
		mForegroundService = findViewById(R.id.foreground_service);
	}

	public void onSinglepixelClicked(View v) {
		Utils.startActivity(this, SinglePixelActivity.class);
	}

	public void onMForegroundServiceClicked(View v) {
		startService(new Intent(this, KeepLiveService.class));
	}
}
