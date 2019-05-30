package com.ilifesmart.barrage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ilifesmart.ui.BarrageView;
import com.ilifesmart.weather.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BarrageActivity extends AppCompatActivity {

	@BindView(R.id.imageView)
	BarrageView mBarrage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_barrage);
		ButterKnife.bind(this);
	}

	@OnClick(R.id.send)
	public void onViewClicked() {
		mBarrage.sendBarrage();
	}
}
