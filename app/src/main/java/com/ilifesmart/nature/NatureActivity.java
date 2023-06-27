package com.ilifesmart.nature;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.ilifesmart.weather.R;

public class NatureActivity extends AppCompatActivity {
	public static final String TAG = "NatureActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nature);
	}

}
