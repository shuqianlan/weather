package com.ilifesmart.os;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.widget.Button;

import com.ilifesmart.weather.R;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OSInfoActivity extends AppCompatActivity {

	public static final String TAG = "OSInfoActivity";
	@BindView(R.id.button)
	Button mButton;
	@BindView(R.id.button2)
	Button mButton2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_osinfo);
		ButterKnife.bind(this);
	}


	@OnClick(R.id.button)
	public void onViewClicked1() {
		Log.d(TAG, "onViewClicked: ");

		Log.d(TAG, "onViewClicked: widthPixels " + getResources().getDisplayMetrics().widthPixels);
		Log.d(TAG, "onViewClicked: heightPixels " + getResources().getDisplayMetrics().heightPixels);

		Log.d(TAG, "onViewClicked: android.OS.Build.BOARD " + Build.BOARD);
		Log.d(TAG, "onViewClicked: android.OS.Build.SDK " + Build.VERSION.SDK_INT);
		Log.d(TAG, "onViewClicked: cpuName " + getCpuName());
		Log.d(TAG, "onViewClicked: FirmwareVersion " + Build.getRadioVersion());
		Log.d(TAG, "onViewClicked: FirmwareVersionAlias " + Build.VERSION.CODENAME);
		Log.d(TAG, "onViewClicked: ReleaseVersion " + Build.VERSION.RELEASE);

		Display display = getWindowManager().getDefaultDisplay();
		Point point = new Point();
		display.getSize(point);
		Log.d(TAG, "onViewClicked: default_width " + point.x);
		Log.d(TAG, "onViewClicked: default_height " + point.y);

		int result = 0;
		int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = getResources().getDimensionPixelSize(resourceId);
			Log.d(TAG, "onViewClicked1: statusBarHeight " + result);
		}

		Log.d(TAG, "onViewClicked1: NavigationBarHeight " + getNavigationBarHeight());

		Point rect = new Point();
		getWindowManager().getDefaultDisplay().getRealSize(rect);
		Log.d(TAG, "onViewClicked1: realSize w " + rect.x);
		Log.d(TAG, "onViewClicked1: realSize h " + rect.y);
	}


	public int getNavigationBarHeight() {
		int result = 0;
		int resourceId=0;
		int rid = getResources().getIdentifier("config_showNavigationBar", "bool", "android");
		if (rid!=0){
			resourceId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
			return getResources().getDimensionPixelSize(resourceId);
		}else
			return 0;
	}

//	public boolean isMatchScreenSize(int expectWidth, int expectHeight, Context context) {
//		boolean isMatch = false;
//		Point rect = new Point();
//		context.getApplicationContext().getResources().getDisplayMetrics();
//	}

	/**
	 * 获取CPU型号
	 *
	 * @return
	 */
	public static String getCpuName() {

		String str1 = "/proc/cpuinfo";
		String str2 = "";

		try {
			FileReader fr = new FileReader(str1);
			BufferedReader localBufferedReader = new BufferedReader(fr);
			while ((str2 = localBufferedReader.readLine()) != null) {
				if (str2.contains("Hardware")) {
					return str2.split(":")[1];
				}
			}
			localBufferedReader.close();
		} catch (IOException e) {
		}
		return null;

	}

	@OnClick(R.id.button2)
	public void onViewClicked() {

	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		return super.dispatchKeyEvent(event);
	}
}
