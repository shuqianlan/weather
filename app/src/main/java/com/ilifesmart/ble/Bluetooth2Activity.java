package com.ilifesmart.ble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.ilifesmart.weather.R;

import java.util.List;

public class Bluetooth2Activity extends AppCompatActivity {

	public static final String TAG = "Bluetooth2Activity";
	public static final String EXTRA_ARGS = "EXTRA_ARGS";

	private BluetoothDevice mBluetoothDevice;
	private BluetoothGattCallback mGattCallback;
	private BluetoothGatt mBluetoothGatt;
	private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			final String action = intent.getAction();
			int extra = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1);

			Log.d(TAG, "onReceive: action " + action);
			if (action.equals(BluetoothLeService.ACTION_GATT_CONNECTED)) {
				Log.d(TAG, "onReceive: GATT_CONNECTED..");
			} else if (action.equals(BluetoothLeService.ACTION_GATT_DISCONNECTED)) {
				Log.d(TAG, "onReceive: GATT_DISCONNECTED..");
			} else if (action.equals(BluetoothLeService.ACTION_DATA_AVAILABLE)) {
				Log.d(TAG, "onReceive: GATT_DATA_AVAILABLE..");
			} else if (action.equals(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED)) {
				Log.d(TAG, "onReceive: GATT_SERVICES_DISCOVERED..");
			}

			if (extra == BluetoothAdapter.STATE_ON) {
				Toast.makeText(context, "蓝牙已连接", Toast.LENGTH_SHORT).show();
			} else if (extra == BluetoothAdapter.STATE_OFF) {
				Toast.makeText(context, "蓝牙已断开", Toast.LENGTH_SHORT).show();
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bluetooth2);

		mBluetoothDevice = getIntent().getParcelableExtra(EXTRA_ARGS);

		IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
		filter.addAction(BluetoothGattInstance.ACTION_GATT_CONNECTED);
		filter.addAction(BluetoothGattInstance.ACTION_GATT_DISCONNECTED);
		filter.addAction(BluetoothGattInstance.ACTION_GATT_SERVICES_DISCOVERED);
		filter.addAction(BluetoothGattInstance.ACTION_DATA_AVAILABLE);

		registerReceiver(mReceiver, filter);
		connectGattServer();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mReceiver);
	}

	public void connectGattServer() {
		AsyncTask.THREAD_POOL_EXECUTOR.execute(new Runnable() {
			@Override
			public void run() {
				if (mBluetoothDevice != null) {
					mBluetoothGatt = mBluetoothDevice.connectGatt(Bluetooth2Activity.this, false, new BluetoothGattInstance(Bluetooth2Activity.this));
				}
			}
		});
	}

	private void displayGattServices(List<BluetoothGattService> gattServices) {

	}
}
