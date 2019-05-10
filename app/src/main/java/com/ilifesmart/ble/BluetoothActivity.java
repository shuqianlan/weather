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
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BluetoothActivity extends AppCompatActivity {

	public static final String TAG = "BluetoothActivity";

	@BindView(R.id.ble_recyclerview)
	RecyclerView mBleRecyclerview;
	private BluetoothAdapter mBluetoothAdapter;
	private int BLUETOOTH_REQUEST_CODE = 20086;
	private BluetoothAdapter.LeScanCallback mLeScanCallback;

	public final static UUID UUID_QUANLIGHT = UUID.fromString("ec963a75-839c-4986-a0e5-b130491c5552");

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
			}
		};

		mBleRecyclerview.setAdapter(mUIAdapter);
		mBleRecyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
	}

	@OnClick(R.id.blue_cap)
	public void onBluetoothCapacity() {
		if (!isHasBluetoothCapacity()) {
			makeToastShow("无蓝牙能力!");
		} else {
			makeToastShow("有蓝牙能力");
		}
	}

	private boolean isHasBluetoothCapacity() {
		return getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE);
	}

	public void makeToastShow(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}

	public void enableBluetooth() {
		if (isHasBluetoothCapacity()) {
			if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
				Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
				startActivityForResult(i, BLUETOOTH_REQUEST_CODE);
			} else {
				makeToastShow("蓝牙已打开");
				startBLEScan();
			}
		} else {
			makeToastShow("设备不支持蓝牙!");
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.d(TAG, "onResume: -----------> ");
		enableBluetooth();
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.d(TAG, "onStart: ------------->");
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.d(TAG, "onPause: ------------->");
		stopBLEScan();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == BLUETOOTH_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				makeToastShow("蓝牙已打开");
				startBLEScan();
			} else {
				makeToastShow("蓝牙未打开");
			}
		}
	}

	private void startBLEScan() {
		if (mBluetoothAdapter != null) {
			//
			Log.d(TAG, "startBLEScan: ..");
			mBluetoothAdapter.startLeScan(new UUID[]{UUID_QUANLIGHT},mLeScanCallback);
		}
	}

	private void stopBLEScan() {
		if (mBluetoothAdapter != null) {
			Log.d(TAG, "stopBLEScan: ..");
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

	public void connect2Gatt(BluetoothDevice device) {
		Intent i = new Intent(this, Bluetooth2Activity.class);
		i.putExtra(Bluetooth2Activity.EXTRA_ARGS, device);
		startActivity(i);
	}

	public class BleItem extends RecyclerView.ViewHolder implements View.OnClickListener {
		private BluetoothDevice mBluetoothDevice;

		@BindView(R.id.ble_name)
		public TextView mBleName;

		@BindView(R.id.ble_adress)
		public TextView mBleAddress;

		public BleItem(@NonNull View itemView) {
			super(itemView);
			itemView.setOnClickListener(this);
			ButterKnife.bind(this, itemView);
		}

		void onBind(BluetoothDevice device) {
			this.mBluetoothDevice = device;
			Log.d("BleItem", "onBind: name " + device.getName());
			Log.d("BleItem", "onBind: address " + device.getAddress());

			String name = device.getName();
			if (TextUtils.isEmpty(name)) {
				name = "Nane";
			}
			String address = device.getAddress();
			mBleName.setText(name);
			mBleAddress.setText(address);
		};

		@Override
		public void onClick(View v) {
//			BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(mBluetoothDevice.getAddress());// 基于此，所以只需传递address即可找到BluetoothDevice对象
//			Log.d(TAG, "onClick: device.address " + device.getAddress());
//			Log.d(TAG, "onClick: mBluetoothDevice.address " + mBluetoothDevice.getAddress());
			connect2Gatt(mBluetoothDevice);
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
			View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ble_item_device, viewGroup, false);
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
