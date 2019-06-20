package com.jni;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.ilifesmart.weather.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JniDemoActivity extends AppCompatActivity {

	static {
		System.loadLibrary("Hello");
	}

	@BindView(R.id.content)
	TextView mContent;

	private static native String hello();
	private static native int add(int a, int b);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jni_demo);
		ButterKnife.bind(this);
	}

	@OnClick(R.id.call_from_c)
	public void onCallFromCClicked() {
		mContent.setText(hello());
	}

	@OnClick(R.id.call_from_Java)
	public void onCallFromJavaClicked() {
		String result = Integer.toString(add(3, 4));
		mContent.setText(result);
	}

}
