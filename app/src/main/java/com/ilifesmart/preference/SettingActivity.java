package com.ilifesmart.preference;

import androidx.preference.PreferenceFragment;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.preference.PreferenceFragmentCompat;

import com.ilifesmart.weather.R;

public class SettingActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_settings);
		getSupportFragmentManager().beginTransaction().replace(R.id.frame_cont, SettingsFragment.newInstance()).commit();

	}

}
