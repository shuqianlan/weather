package com.ilifesmart.mapper;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.ilifesmart.listener.INotiListener;
import com.ilifesmart.model.MOBase;
import com.ilifesmart.weather.R;

import java.util.concurrent.atomic.AtomicInteger;


public class MapperActivity extends AppCompatActivity {

	public static final String TAG = "MapperActivity";
	Button mButton3;
	private MOBase mo;
	private MOBase alias;
	private AtomicInteger index = new AtomicInteger(0);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mapper);

		mButton3 = findViewById(R.id.button3);
		mo = new MOBase();
	}

	public void onViewClicked(View v) {
		setRegister(mo, new INotiListener() {
			@Override
			public void onChg(MOBase mo) {

			}
		});
		Log.d(TAG, "onViewClicked: index " + index.incrementAndGet());
		Log.d(TAG, "onViewClicked: org_size " + mo.getListenersSize());
	}

	private void setRegister(MOBase mo, INotiListener listener) {
		alias = mo;
		mo.registerListener(listener);
		Log.d(TAG, "setRegister: alias.size " + alias.getListenersSize());
		Log.d(TAG, "setRegister: mo.size " + mo.getListenersSize());
	}
}
