package com.ilifesmart.barrage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.ilifesmart.ui.SurfaceVideoView;
import com.ilifesmart.weather.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VideoActivity extends AppCompatActivity {

	@BindView(R.id.play)
	Button mPlay;
	@BindView(R.id.send)
	Button mSend;
	@BindView(R.id.pause)
	Button mPause;
	@BindView(R.id.frame_cont)
	SurfaceVideoView mVideoCont;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_barrage);
		ButterKnife.bind(this);

		mVideoCont.setVideoPath("video/Rise_of_Ascended.mp4");
	}

	@OnClick(R.id.send)
	public void onBarrageClicked() {
		mVideoCont.sendBarrage();
	}

	@Override
	protected void onDestroy() {
		mVideoCont.onDestroyed();
		super.onDestroy();
	}

}
