package com.ilifesmart.live;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.ilifesmart.utils.Utils;
import com.ilifesmart.weather.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProgressLiveActivity extends AppCompatActivity {

	@BindView(R.id.singlepixel)
	Button mSinglepixel;
	@BindView(R.id.foreground_service)
	Button mForegroundService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_progress_live);
		ButterKnife.bind(this);
	}

	@OnClick(R.id.singlepixel)
	public void onSinglepixelClicked() {
		Utils.startActivity(this, SinglePixelActivity.class);
	}

	@OnClick(R.id.foreground_service)
	public void onMForegroundServiceClicked() {
		startService(new Intent(this, KeepLiveService.class));
	}
}
