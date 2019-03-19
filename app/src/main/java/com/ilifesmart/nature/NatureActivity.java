package com.ilifesmart.nature;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.ilifesmart.ui.MyConstrainLayout;
import com.ilifesmart.weather.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NatureActivity extends AppCompatActivity {
	public static final String TAG = "NatureActivity";
	@BindView(R.id.nature_cont)
	LinearLayout mNatureCont;
	@BindView(R.id.cont_1)
	MyConstrainLayout mCont1;
	@BindView(R.id.cont_2)
	MyConstrainLayout mCont2;
	@BindView(R.id.cont_3)
	MyConstrainLayout mCont3;
	@BindView(R.id.cont_4)
	MyConstrainLayout mCont4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nature);
		ButterKnife.bind(this);

		mCont1.setBackground(new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] {Color.BLUE, Color.LTGRAY}));
		mCont2.setBackground(new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] {Color.BLUE, Color.LTGRAY}));
		mCont3.setBackground(new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] {Color.BLUE, Color.LTGRAY}));
		mCont4.setBackground(new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] {Color.BLUE, Color.LTGRAY}));
	}

}
