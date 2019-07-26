package com.jni;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import com.ilifesmart.weather.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class JniDemoActivity extends AppCompatActivity {

	public static final String TAG = "JniDemoActivity";

	static {
		System.loadLibrary("Hello");
	}

	HelloNDK instance;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jni_demo);
		ButterKnife.bind(this);

		instance = new HelloNDK();
	}

	@OnClick(R.id.c_print)
	public void onCPrintClicked() {
		HelloNDK.callFromC();
	}

	@OnClick(R.id.c_add)
	public void onCAddClicked() {
		int result = HelloNDK.callAddFromC(3, 4);
		Log.d(TAG, "onCAddClicked: Reselt_from_c " + result);
	}

	@OnClick(R.id.c_string_append)
	public void onCStringAppendClicked() {
		String rtn = HelloNDK.callStringAppend("Hello");
		Log.d(TAG, "onCStringAppendClicked: rtn " + rtn);
	}

	@OnClick(R.id.java_debug)
	public void onJavaDebugClicked() {
		instance.callDebugFromJava();
	}

	@OnClick(R.id.java_add)
	public void onJavaAddClicked() {
		int result = instance.callAddFromJava();
		Log.d(TAG, "onJavaAddClicked: result " + result);
	}

	@OnClick(R.id.java_print)
	public void onJavaPrintClicked() {
		instance.callPrintFromJava();
	}

}
