package com.ilifesmart.barrage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ilifesmart.ui.BarrageView;
import com.ilifesmart.weather.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BarrageActivity extends AppCompatActivity {

	@BindView(R.id.imageView)
	BarrageView mImageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_barrage);
		ButterKnife.bind(this);
	}

	@OnClick({R.id.button4, R.id.button5})
	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.button4:
				mImageView.startRunning();
				break;
			case R.id.button5:
				mImageView.stopRunning();
				break;
		}
	}
}
