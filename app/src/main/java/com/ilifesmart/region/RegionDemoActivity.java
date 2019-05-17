package com.ilifesmart.region;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.ilifesmart.weather.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegionDemoActivity extends AppCompatActivity {

	@BindView(R.id.region_demo_top)
	Button mRegionDemoTop;

	private String code;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_region_demo);
		ButterKnife.bind(this);
	}

	@OnClick(R.id.region_demo_top)
	public void onViewClicked() {
		PopupRegionWindow window = new PopupRegionWindow(this);
		window.setRegionCode(code).setOnRegionSelectedListener(new PopupRegionWindow.OnRegionSelectedListener() {
			@Override
			public void onRegionSelected(String code, String name) {
				mRegionDemoTop.setText(name);
				RegionDemoActivity.this.code = code;
			}
		}).show();
	}
}
