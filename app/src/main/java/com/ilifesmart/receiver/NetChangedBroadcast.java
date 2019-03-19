package com.ilifesmart.receiver;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.ilifesmart.utils.NetWorkUtil;
import com.ilifesmart.utils.Utils;

public class NetChangedBroadcast extends BroadcastReceiver {

	public static final String TAG = "NetChangedBroadcast";
	private static AlertDialog sAlertDialog;
	@Override
	public void onReceive(Context context, Intent intent) {

		if (NetWorkUtil.isNetworkConnected(context)) {
			Log.d(TAG, "onReceive: Accessable");
			if (sAlertDialog != null) {
				sAlertDialog.dismiss();
			}
		} else {
			Log.d(TAG, "onReceive: unaccessable");
			(sAlertDialog=Utils.creatLoadingDialog(context)).show();
		}
	}
}
