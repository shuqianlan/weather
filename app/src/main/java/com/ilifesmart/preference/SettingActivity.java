package com.ilifesmart.preference;

import android.annotation.TargetApi;
import android.os.Build;
import android.preference.PreferenceActivity;
import android.support.v14.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.ilifesmart.weather.R;

import java.util.List;

public class SettingActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_settings);
		getSupportFragmentManager().beginTransaction().replace(R.id.frame_cont, SettingsFragment.newInstance()).commit();

	}

}
