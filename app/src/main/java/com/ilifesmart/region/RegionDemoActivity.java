package com.ilifesmart.region;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

import com.ilifesmart.weather.R;

public class RegionDemoActivity extends AppCompatActivity {

	Button mRegionDemoTop;

	private String code;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_region_demo);

		mRegionDemoTop = findViewById(R.id.region_demo_top);
	}

	public void onViewClicked(View v) {
		PopupRegionWindow window = new PopupRegionWindow(this);
		window.setRegionCode(code).setOnRegionSelectedListener(new PopupRegionWindow.OnRegionSelectedListener() {
			@Override
			public void onRegionSelected(String code, String name) {
				mRegionDemoTop.setText(name.concat("(").concat(code).concat(")"));
				RegionDemoActivity.this.code = code;
			}
		}).show();
	}
}
