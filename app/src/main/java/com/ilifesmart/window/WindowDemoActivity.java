package com.ilifesmart.window;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.ilifesmart.weather.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class WindowDemoActivity extends AppCompatActivity {

	private WindowManager mWindowManager;
	private WindowManager.LayoutParams mWindowLayoutParams;

	private ImageView mImageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_window_demo);
		ButterKnife.bind(this);

		mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		requestPermissions();
	}

	private void requestPermissions() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			if (!Settings.canDrawOverlays(this)) {
				Intent i = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
				startActivityForResult(i, 10);
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 10) {
			requestPermissions();
		}
	}

	@OnClick({R.id.add, R.id.remove})
	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.add:
				onWindAddView();
				break;
			case R.id.remove:
				onWindRemoveView();
				break;
		}
	}

	private void onWindAddView() {
		mImageView = new ImageView(this);
		mImageView.setBackgroundResource(R.drawable.bulb);
		mImageView.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int rawX = (int) event.getRawX();
				int rawY = (int) event.getRawY();

				switch (event.getAction()) {
					case MotionEvent.ACTION_MOVE:
						mWindowLayoutParams.x = rawX-mImageView.getWidth()/2;
						mWindowLayoutParams.y = rawY-mImageView.getHeight()/2;
						mWindowManager.updateViewLayout(mImageView, mWindowLayoutParams);
				}
				return false;
			}
		});
		mWindowLayoutParams = new WindowManager.LayoutParams(
						ViewGroup.LayoutParams.WRAP_CONTENT,
						ViewGroup.LayoutParams.WRAP_CONTENT,
						WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
						WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | // 不获取焦点
						WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED,       // 锁屏时展示
						PixelFormat.TRANSPARENT                                 // 透明度
		);

		mWindowLayoutParams.gravity = Gravity.TOP | Gravity.LEFT;
		mWindowLayoutParams.x = 0;
		mWindowLayoutParams.y=0;
		mWindowManager.addView(mImageView, mWindowLayoutParams);
	}

	private void onWindRemoveView() {
		if (mImageView != null) {
			mWindowManager.removeView(mImageView);
			mImageView = null;
		}

	}

}
