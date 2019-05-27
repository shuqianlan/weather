package com.ilifesmart.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ilifesmart.weather.R;

public abstract class LazyFragment extends Fragment {


	public LazyFragment() {
		// Required empty public constructor
	}

	protected boolean isVisible;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
													 Bundle savedInstanceState) {
		TextView textView = new TextView(getActivity());
		textView.setText(R.string.hello_blank_fragment);
		return textView;
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);

		isVisible =isVisibleToUser;

		if (isVisible) {
			loadData();
		}
	}

	protected abstract void loadData();
}
