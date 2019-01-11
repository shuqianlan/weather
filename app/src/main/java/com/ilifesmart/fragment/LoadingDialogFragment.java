package com.ilifesmart.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ilifesmart.weather.R;

public class LoadingDialogFragment extends DialogFragment {

	public static LoadingDialogFragment newInstance() {
		Bundle args = new Bundle();
		LoadingDialogFragment fragment = new LoadingDialogFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@NonNull
	@Override
	public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), getTheme());
		View v = LayoutInflater.from(getContext()).inflate(R.layout.fragment_loading_dialog, null);
		builder.setView(v);
		return builder.create();
	}


}
