package com.ilifesmart.compass;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import com.ilifesmart.ui.EmbeddedCompassView;
import com.ilifesmart.weather.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CompassActivity extends AppCompatActivity {

	public static final String TAG = "CompassActivity";
	@BindView(R.id.compass)
	EmbeddedCompassView mCompass;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compass);
		ButterKnife.bind(this);

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
