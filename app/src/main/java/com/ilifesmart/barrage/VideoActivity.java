package com.ilifesmart.barrage;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;

import com.ilifesmart.ui.SurfaceVideoView;
import com.ilifesmart.weather.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class VideoActivity extends AppCompatActivity {

	@BindView(R.id.send)
	Button mSend;
	@BindView(R.id.frame_cont)
	SurfaceVideoView mVideoCont;
	@BindView(R.id.barrage_switch)
	CheckBox mBarrageSwitch;
	@BindView(R.id.barrage_content)
	EditText mBarrageContent;
	@BindView(R.id.thumbnail)
	ImageView mThumbnail;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_barrage);
		ButterKnife.bind(this);

		String filePath = "video/Rise_of_Ascended.mp4";
		mVideoCont.setVideoPath(filePath);
	}

	@Override
	protected void onDestroy() {
		mVideoCont.onDestroyed();
		super.onDestroy();
	}

	@RequiresApi(api = Build.VERSION_CODES.M)
	@OnClick({R.id.speed_1, R.id.speed_2, R.id.speed_3, R.id.speed_4, R.id.send})
	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.speed_1:
				mVideoCont.setVideoSpeed(0.5f);
				break;
			case R.id.speed_2:
				mVideoCont.setVideoSpeed(1.0f);
				break;
			case R.id.speed_3:
				mVideoCont.setVideoSpeed(1.5f);
				break;
			case R.id.speed_4:
				mVideoCont.setVideoSpeed(2.0f);
				break;
			case R.id.send:
				String barrage = mBarrageContent.getText().toString();
				if (TextUtils.isEmpty(barrage)) {
					mVideoCont.sendBarrage();
				} else {
					mVideoCont.sendBarrage(barrage);
				}
				mBarrageContent.setText("");
				break;
		}
	}

	@OnCheckedChanged(R.id.barrage_switch)
	public void OnChecked(CompoundButton button, boolean closed) {
		mVideoCont.setBarrageClosed(closed);
	}

}
