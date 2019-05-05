package com.ilifesmart.live;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.ilifesmart.broad.ScreenBroadcastListener;
import com.ilifesmart.weather.R;

public class SinglePixelActivity extends AppCompatActivity {

	public static final String TAG = "SinglePixelActivity";

	public static void actionToSinglePixelActivity(Context context) {
		Intent i = new Intent(context, SinglePixelActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(i);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_single_pixel);

		Window window = getWindow();
		window.setGravity(Gravity.START | Gravity.TOP);
		WindowManager.LayoutParams attributes = window.getAttributes();

		attributes.width = 1;
		attributes.height = 1;
		attributes.x = 0;
		attributes.y = 0;
		window.setAttributes(attributes);

		ScreenBroadcastListener.ScreenManager.getInstance(this).setActivity(this);
	}
}
