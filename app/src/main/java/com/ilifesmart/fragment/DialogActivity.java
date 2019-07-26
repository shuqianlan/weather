package com.ilifesmart.fragment;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.ilifesmart.weather.R;

public class DialogActivity extends AppCompatActivity {

	private DialogFragment mDialogFragment;
	private FragmentManager fm;
	private final String DIALOG_FRAGMENT_TAG = "LOADING_DIALOG_FRAGMENT";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dialog);

//		mDialogFragment = LoadingDialogFragment.newInstance();
//		fm = getSupportFragmentManager();
//		mDialogFragment.show(fm, DIALOG_FRAGMENT_TAG);
	}

	@Override
	protected void onDestroy() {
//		mDialogFragment.dismissAllowingStateLoss();
		super.onDestroy();
	}
}
