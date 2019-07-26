package com.ilifesmart.layout;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;
import android.view.ViewStub;

import com.ilifesmart.weather.R;

import butterknife.ButterKnife;

public class AbstractActivity extends AppCompatActivity {

	public static final String TAG = "AbstractLayout";

//	@BindView(R.id.viewStub)
//	ViewStub mViewStub;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_abstract);
		ButterKnife.bind(this);
		
		new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
			@Override
			public void run() {
				((ViewStub)findViewById(R.id.viewStub)).inflate();
			}
		}, 1000);
	}

}
