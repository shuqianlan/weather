package com.ilifesmart.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ilifesmart.weather.R;

public class LazyDemoFragment extends LazyFragment {
	public LazyDemoFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
													 Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_lazy_demo, container, false);
	}


	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
	}

	@Override
	protected void loadData() {

	}

	@Override
	public void onDetach() {
		super.onDetach();
	}

}
