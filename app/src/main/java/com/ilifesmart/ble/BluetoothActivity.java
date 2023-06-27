package com.ilifesmart.ble;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class BluetoothActivity extends AppCompatActivity {

	public static final String TAG = "BluetoothActivity";

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

		mBleRecyclerview = findViewById(R.id.ble_recyclerview);

	}

	private boolean isPermissionGranted(String perm) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			return checkSelfPermission(perm) == PackageManager.PERMISSION_GRANTED;
		}
		return true;
	}

	private void checkPermissions() {
		boolean isGranted = false;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
			if (!isPermissionGranted(Manifest.permission.BLUETOOTH_SCAN)) {
				self:
				requestPermission();
				return;
			}
		} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			if (!isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
				self:
				requestPermission();
				return;
			}
		}

		initialBluetooth();
	}

	private void initialBluetooth() {
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
		mBleRecyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

		enableBluetooth();
	}

	private int PERMISSION_CODE = 1111;

	private void requestPermission() {
		List<String> perms = new ArrayList<>();

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
			perms.add(Manifest.permission.BLUETOOTH_SCAN);
			perms.add(Manifest.permission.BLUETOOTH_CONNECT);
			perms.add(Manifest.permission.BLUETOOTH_ADVERTISE);
		} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			perms.add(Manifest.permission.ACCESS_FINE_LOCATION);
		}

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			this.requestPermissions((String[]) perms.toArray(), PERMISSION_CODE);
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);

		if (requestCode == PERMISSION_CODE) {
			int size = 0;
			for (int i = 0; i < permissions.length; i++) {
				boolean isGranted = (grantResults[i] == PackageManager.PERMISSION_GRANTED);
				Log.d(TAG, "onRequestPermissionsResult: permission " + permissions[i] + " isGranted " + isGranted);
				if (isGranted) {
					size++;
				}
			}

			if (size == permissions.length) {
				initialBluetooth();
			}
		}
	}

	public void onBluetoothCapacity(View v) {
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
			if (isPermissionGranted(Manifest.permission.BLUETOOTH_CONNECT)) {
				if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
					Intent i = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
						if (checkSelfPermission(Manifest.permission.BLUETOOTH_SCAN) == PackageManager.PERMISSION_GRANTED) {
							startActivityForResult(i, BLUETOOTH_REQUEST_CODE);
						}
					}
				} else {
					makeToastShow("蓝牙已打开");
					startBLEScan();
				}
			}
		} else {
			makeToastShow("设备不支持蓝牙!");
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.d(TAG, "onResume: -----------> ");

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
			if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
				// TODO: Consider calling
				//    ActivityCompat#requestPermissions
				// here to request the missing permissions, and then overriding
				//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
				//                                          int[] grantResults)
				// to handle the case where the user grants the permission. See the documentation
				// for ActivityCompat#requestPermissions for more details.
				return;
			}
			mBluetoothAdapter.startLeScan(new UUID[]{UUID_QUANLIGHT}, mLeScanCallback);
		}
	}

	private void stopBLEScan() {
		if (mBluetoothAdapter != null) {
			Log.d(TAG, "stopBLEScan: ..");
			if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
				// TODO: Consider calling
				//    ActivityCompat#requestPermissions
				// here to request the missing permissions, and then overriding
				//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
				//                                          int[] grantResults)
				// to handle the case where the user grants the permission. See the documentation
				// for ActivityCompat#requestPermissions for more details.
				return;
			}
			mBluetoothAdapter.stopLeScan(mLeScanCallback);
		}
	}

	public void onMBlueEnableClicked(View v) {
		enableBluetooth();
	}

	public void onMBlueStartScanClicked(View v) {
		startBLEScan();
	}

	public void onMBlueStopScanClicked(View v) {
		stopBLEScan();
	}

	public void connect2Gatt(BluetoothDevice device) {
		Intent i = new Intent(this, Bluetooth2Activity.class);
		i.putExtra(Bluetooth2Activity.EXTRA_ARGS, device);
		startActivity(i);
	}

	public class BleItem extends RecyclerView.ViewHolder implements View.OnClickListener {
		private BluetoothDevice mBluetoothDevice;

		public TextView mBleName;

		public TextView mBleAddress;

		public BleItem(@NonNull View itemView) {
			super(itemView);
			itemView.setOnClickListener(this);

			mBleName = itemView.findViewById(R.id.ble_name);
			mBleAddress = itemView.findViewById(R.id.ble_adress);
		}

		void onBind(BluetoothDevice device) {
			this.mBluetoothDevice = device;
			if (ActivityCompat.checkSelfPermission(BluetoothActivity.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
				// TODO: Consider calling
				//    ActivityCompat#requestPermissions
				// here to request the missing permissions, and then overriding
				//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
				//                                          int[] grantResults)
				// to handle the case where the user grants the permission. See the documentation
				// for ActivityCompat#requestPermissions for more details.
				return;
			}
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
