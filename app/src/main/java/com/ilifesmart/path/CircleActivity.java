package com.ilifesmart.path;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ilifesmart.ui.QualCircle;
import com.ilifesmart.weather.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CircleActivity extends AppCompatActivity {

	@BindView(R.id.qual_circle)
	QualCircle mQualCircle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_circle);
		ButterKnife.bind(this);
	}

	@OnClick(R.id.startAnim)
	public void onStartAnim() {
		mQualCircle.startAnimation();
	}

}
