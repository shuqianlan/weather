package com.ilifesmart.live;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;

import com.ilifesmart.weather.R;

public class KeepLiveService extends Service {
	public static final int NOTIFICATION_ID = 0x11;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
			startForeground(NOTIFICATION_ID, new Notification());
		} else {
			Notification.Builder builder = new Notification.Builder(this);
			builder.setSmallIcon(R.mipmap.ic_launcher);
			startForeground(NOTIFICATION_ID, builder.build());
			startService(new Intent(this, InnerService.class));
		}
	}

	public static class InnerService extends Service {

		@Override
		public IBinder onBind(Intent intent) {
			return null;
		}

		@Override
		public void onCreate() {
			super.onCreate();

			Notification.Builder builder = new Notification.Builder(this);
			builder.setSmallIcon(R.mipmap.ic_launcher);
			startForeground(NOTIFICATION_ID, builder.build());
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					stopForeground(true);
					NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
					manager.cancel(NOTIFICATION_ID);
					stopSelf();
				}
			},100);		}
	}
}
