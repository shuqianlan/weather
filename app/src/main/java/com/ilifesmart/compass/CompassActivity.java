package com.ilifesmart.compass;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import com.ilifesmart.ui.EmbeddedCompassView;
import com.ilifesmart.weather.R;

public class CompassActivity extends AppCompatActivity {

	public static final String TAG = "CompassActivity";
	EmbeddedCompassView mCompass;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compass);
		mCompass = findViewById(R.id.compass);

		mCompass.setListener(new EmbeddedCompassView.OnAngleChangeListener() {
			@Override
			public void onAngleChanged(double angle, EmbeddedCompassView compassView) {
				Log.d(TAG, "OnAngleChangeListener: angle " + angle);
			}

			@Override
			public void onTouchBegin() {

			}

			@Override
			public void onTouchEnd() {

			}
		});
	}

}
