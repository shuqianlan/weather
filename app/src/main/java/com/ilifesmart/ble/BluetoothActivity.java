package com.ilifesmart.ble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ilifesmart.weather.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BluetoothActivity extends AppCompatActivity {

	@BindView(R.id.ble_recyclerview)
	RecyclerView mBleRecyclerview;
	private BluetoothAdapter mBluetoothAdapter;
	private int BLUETOOTH_REQUEST_CODE = 20086;
	private BluetoothAdapter.LeScanCallback mLeScanCallback;

	private BleAdapter mUIAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bluetooth);
		ButterKnife.bind(this);

		final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
		mBluetoothAdapter = bluetoothManager.getAdapter();
		mUIAdapter = new BleAdapter();

		mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
			@Override
			public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
				mUIAdapter.addDevice(device);
				Log.d("BLE", "onLeScan: device " + device + " rssi " + rssi + " record " + new String(scanRecord));
			}
		};

//		mBleRecyclerview.setAdapter();
		mBleRecyclerview.setAdapter(mUIAdapter);
		mBleRecyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
	}

	@OnClick(R.id.blue_cap)
	public void onBluetoothCapacity() {
		if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
			makeToastShow("无蓝牙能力!");
		} else {
			makeToastShow("有蓝牙能力");
		}
	}

	public void makeToastShow(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}

	public void enableBluetooth() {
		if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
			Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(i, BLUETOOTH_REQUEST_CODE);
		} else {
			makeToastShow("蓝牙已打开");
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == BLUETOOTH_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				makeToastShow("蓝牙已打开");
			} else {
				makeToastShow("蓝牙未打开");
			}
		}
	}

	private void startBLEScan() {
		if (mBluetoothAdapter != null) {
			mBluetoothAdapter.startLeScan(mLeScanCallback);
		}
	}

	private void stopBLEScan() {
		if (mBluetoothAdapter != null) {
			mBluetoothAdapter.stopLeScan(mLeScanCallback);
		}
	}

	@OnClick(R.id.blue_enable)
	public void onMBlueEnableClicked() {
		enableBluetooth();
	}

	@OnClick(R.id.blue_start_scan)
	public void onMBlueStartScanClicked() {
		startBLEScan();
	}

	@OnClick(R.id.blue_stop_scan)
	public void onMBlueStopScanClicked() {
		stopBLEScan();
	}

	public void connect2Gatt() {

	}

	public class BleItem extends RecyclerView.ViewHolder implements View.OnClickListener {
		private BluetoothDevice mBluetoothDevice;

		public BleItem(@NonNull View itemView) {
			super(itemView);
			itemView.setOnClickListener(this);
		}

		void onBind(BluetoothDevice device) {
			this.mBluetoothDevice = device;
			Log.d("BleItem", "onBind: name " + device.getName());
			Log.d("BleItem", "onBind: address " + device.getAddress());

			String name = device.getName();
			String address = device.getAddress();
			String title = address;
			if (!TextUtils.isEmpty(name)) {
				title += " ("+name+")";
			}
			((TextView)(this.itemView)).setText(title);
		};

		@Override
		public void onClick(View v) {
			Log.d("BleItem", "onBind: name " + mBluetoothDevice.getName());
			Log.d("BleItem", "onClick: blueDevice " + mBluetoothDevice);
		}
	}

	public class BleAdapter extends RecyclerView.Adapter<BleItem> {
		private List<BluetoothDevice> mBluetoothDevices = new ArrayList<>();

		public void addDevice(BluetoothDevice device) {
			synchronized (mBluetoothDevices) {
				if (!mBluetoothDevices.contains(device)) {
					mBluetoothDevices.add(device);
					notifyDataSetChanged();
				}
			}
		}

		@NonNull
		@Override
		public BleItem onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
			View v = LayoutInflater.from(viewGroup.getContext()).inflate(android.R.layout.simple_list_item_1, viewGroup, false);
			return new BleItem(v);
		}

		@Override
		public void onBindViewHolder(@NonNull BleItem bleItem, int i) {
			bleItem.onBind(mBluetoothDevices.get(i));
		}

		@Override
		public int getItemCount() {
			Log.d("BleAdapter", "getItemCount: size " + mBluetoothDevices.size());
			return mBluetoothDevices.size();
		}
	}
}
