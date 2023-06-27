package com.jni;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.ilifesmart.weather.R;

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

		instance = new HelloNDK();
	}

	public void onCPrintClicked(View v) {
		HelloNDK.callFromC();
	}

	public void onCAddClicked(View v) {
		int result = HelloNDK.callAddFromC(3, 4);
		Log.d(TAG, "onCAddClicked: Reselt_from_c " + result);
	}

	public void onCStringAppendClicked(View v) {
		String rtn = HelloNDK.callStringAppend("Hello");
		Log.d(TAG, "onCStringAppendClicked: rtn " + rtn);
	}

	public void onJavaDebugClicked(View v) {
		instance.callDebugFromJava();
	}

	public void onJavaAddClicked(View v) {
		int result = instance.callAddFromJava();
		Log.d(TAG, "onJavaAddClicked: result " + result);
	}

	public void onJavaPrintClicked(View v) {
		instance.callPrintFromJava();
	}

}
