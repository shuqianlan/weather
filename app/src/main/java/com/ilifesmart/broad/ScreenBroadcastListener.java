package com.ilifesmart.broad;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.ilifesmart.live.SinglePixelActivity;

import java.lang.ref.WeakReference;

public class ScreenBroadcastListener {

	private Context mContext;
	private ScreenBroadcastReceiver mScreenReceiver;
	private ScreenStateListener mListener;

	public ScreenBroadcastListener(Context context) {
		mContext = context.getApplicationContext();
		mScreenReceiver = new ScreenBroadcastReceiver();
	}

	public interface ScreenStateListener {
		void onScreenOn();
		void onScreenOff();
	}

	private class ScreenBroadcastReceiver extends BroadcastReceiver {
		private String action = null;
		@Override
		public void onReceive(Context context, Intent intent) {
			action = intent.getAction();
			if (Intent.ACTION_SCREEN_ON.equals(action)) {
				mListener.onScreenOn();
			} else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
				mListener.onScreenOff();
			}
		}
	}

	public void registerListener(ScreenStateListener listener) {
		mListener = listener;
		registerListener();
	}

	public void registerListener() {
		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_SCREEN_ON);
		filter.addAction(Intent.ACTION_SCREEN_OFF);
		mContext.registerReceiver(mScreenReceiver, filter);
	}

	public static class ScreenManager {
		private Context mContext;
		private WeakReference<Activity> mActivityWeakReference;
		public static ScreenManager mDefault;

		public ScreenManager(Context context) {
			mContext = context;
		}

		public static ScreenManager getInstance(Context context) {
			if (mDefault == null) {
				mDefault = new ScreenManager(context);
			}

			return mDefault;
		}

		public void setActivity(Activity activity) {
			mActivityWeakReference = new WeakReference<>(activity);
		}

		public void startActivity() {
			SinglePixelActivity.actionToSinglePixelActivity(mContext);
		}

		public void finishActivity() {
			if (mActivityWeakReference != null) {
				Activity activity = mActivityWeakReference.get();
				if (activity != null && !activity.isFinishing()) {
					activity.finish();
				}
			}
		}
	}
}
