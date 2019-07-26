package com.ilifesmart.framelayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.ilifesmart.ui.EmbeddedView;
import com.ilifesmart.ui.EmbeddedViewBuilder;
import com.ilifesmart.ui.EmbeddedViewStackContainer;
import com.ilifesmart.weather.R;

import java.util.concurrent.atomic.AtomicInteger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FrameLayoutActivity extends AppCompatActivity {

	public static final String TAG = "FrameLayoutActivity";
	@BindView(R.id.frame_cont)
	EmbeddedViewStackContainer mFrameCont;
	private AtomicInteger counts = new AtomicInteger(0);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_frame_layout);
		ButterKnife.bind(this);
	}

	@OnClick(R.id.frame_cont)
	public void onViewClicked() {
		View item = LayoutInflater.from(this).inflate(R.layout.activity_frame_layout_item1, mFrameCont, false);
		EmbeddedView v = new EmbeddedViewBuilder().setIdentifier("item-1").setUIView(item).build();
		item.findViewById(R.id.item_1_cont).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mFrameCont.popView();
			}
		});

		mFrameCont.pushView(v);
	}

	public static Intent newIntent(Context context) {
		return new Intent(context, FrameLayoutActivity.class);
	}
}
