package com.ilifesmart.preference;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

import com.ilifesmart.weather.R;

import java.util.ArrayList;
import java.util.List;

public class SettingsFragment extends PreferenceFragmentCompat {
	public static final String TAG = "SettingsFragment";

	public static SettingsFragment newInstance() {
		return new SettingsFragment();
	}

	private final static List<String> keyItems = new ArrayList<>();
	private final String KEY_ITEM_1 = "item-1";
	private final String KEY_ITEM_2 = "item-2";
	private final String KEY_ITEM_3 = "item-3";
	private final String KEY_ITEM_4 = "item-4";

	static {
		for (int i = 1; i < 5; i++) {
			keyItems.add("item-"+i);
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate: getArgs " + getArguments());
//		getPreferenceManager().setSharedPreferencesMode(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//		getPreferenceManager().setSharedPreferencesMode(FLAG_GRANT_WRITE_URI_PERMISSION);
		addPreferencesFromResource(R.xml.pref_example);
	}

	@Override
	public void onCreatePreferences(Bundle bundle, String s) {
		Log.d(TAG, "onCreatePreferences: rootKey " + s);
//		addPreferencesFromResource(R.xml.pref_example);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		for (int i = 0; i < keyItems.size(); i++) {
			findPreference(keyItems.get(i)).setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
				@Override
				public boolean onPreferenceClick(Preference preference) {
					Log.d(TAG, "onPreferenceClick: currKey " + preference.getKey());
					return true;
				}
			});
		}
	}
}
