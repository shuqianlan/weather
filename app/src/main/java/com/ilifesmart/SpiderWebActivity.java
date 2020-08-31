package com.ilifesmart;

import android.os.Bundle;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

import com.ilifesmart.ui.SpiderWeb;
import com.ilifesmart.weather.R;

public class SpiderWebActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_spider_web);

		SpiderWeb spiderWeb = findViewById(R.id.spider_web);
		((SeekBar)findViewById(R.id.spiders)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				spiderWeb.setSpiderCounts(progress);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}
		});

		((SeekBar)findViewById(R.id.speeds)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}
		});

		((SeekBar)findViewById(R.id.spiders)).setProgress(spiderWeb.getSpiderCounts());
	}
}
