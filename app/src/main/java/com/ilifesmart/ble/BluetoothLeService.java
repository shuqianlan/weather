package com.ilifesmart.ble;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.UUID;

public class BluetoothLeService extends Service {
	@Override
	public IBinder onBind(Intent intent)
	{
		sContext = getApplicationContext();
		return null;
	}



	public static final String TAG = BluetoothLeService.class.getSimpleName();
	private static Context sContext;

	private BluetoothManager mBluetoothManager;
	private BluetoothAdapter mBluetoothAdapter;
	private String mBluetoothDeviceAddress;
	private BluetoothGatt mBluetoothGatt;
	private static int connectionState = BluetoothProfile.STATE_DISCONNECTED;

	public final static String ACTION_GATT_CONNECTED =
					"com.example.bluetooth.le.ACTION_GATT_CONNECTED";
	public final static String ACTION_GATT_DISCONNECTED =
					"com.example.bluetooth.le.ACTION_GATT_DISCONNECTED";
	public final static String ACTION_GATT_SERVICES_DISCOVERED =
					"com.example.bluetooth.le.ACTION_GATT_SERVICES_DISCOVERED";
	public final static String ACTION_DATA_AVAILABLE =
					"com.example.bluetooth.le.ACTION_DATA_AVAILABLE";
	public final static String EXTRA_DATA =
					"com.example.bluetooth.le.EXTRA_DATA";

	public static final UUID CharacteristicUUID_SN = parseUUIDFrom(new byte[]{(byte) 0x25, (byte) 0x2A});
	public static final UUID CharacteristicUUID_SoftwareVersion = parseUUIDFrom(new byte[]{(byte) 0x28, (byte) 0x2A});

	public static UUID parseUUIDFrom(byte[] uuidBytes) {
		final UUID BASE_UUID = UUID.fromString("00000000-0000-1000-8000-00805F9B34FB");
		if (uuidBytes == null) return null;
		int length = uuidBytes.length;
		if (length != 2 && length != 4 && length != 16) return null;

		// Construct a 128 bit UUID.
		if (length == 16) {
			ByteBuffer buf = ByteBuffer.wrap(uuidBytes).order(ByteOrder.LITTLE_ENDIAN);
			long msb = buf.getLong(8);
			long lsb = buf.getLong(0);
			return new UUID(msb, lsb);
		}

		// For 16 bit and 32 bit UUID we need to convert them to 128 bit value.
		// 128_bit_value = uuid * 2^96 + BASE_UUID
		long shortUuid;
		if (length == 2) {
			shortUuid = uuidBytes[0] & 0xFF;
			shortUuid += (uuidBytes[1] & 0xFF) << 8;
		} else {
			shortUuid = uuidBytes[0] & 0xFF ;
			shortUuid += (uuidBytes[1] & 0xFF) << 8;
			shortUuid += (uuidBytes[2] & 0xFF) << 16;
			shortUuid += (uuidBytes[3] & 0xFF) << 24;
		}
		long msb = BASE_UUID.getMostSignificantBits() + (shortUuid << 32);
		long lsb = BASE_UUID.getLeastSignificantBits();
		return new UUID(msb, lsb);
	}

	public static final BluetoothGattCallback gattCallback = new BluetoothGattCallback() {
		@Override
		public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
			String intentAction;
			connectionState = newState;
			if (newState == BluetoothProfile.STATE_CONNECTED) {
				intentAction = ACTION_GATT_CONNECTED;
				broadcastUpdate(intentAction);
			} else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
				intentAction = ACTION_GATT_DISCONNECTED;
				broadcastUpdate(intentAction);
			}
		}

		@Override
		public void onServicesDiscovered(BluetoothGatt gatt, int status) {
			if (status == BluetoothGatt.GATT_SUCCESS) {
				Log.d(TAG, "onServicesDiscovered: new Discovered");
				broadcastUpdate(ACTION_GATT_SERVICES_DISCOVERED);
			}
		}

		// 读操作的结果.
		@Override
		public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
//			super.onCharacteristicRead(gatt, characteristic, status);
			if (status == BluetoothGatt.GATT_SUCCESS) {
				broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);
			}
		}
	};

	public static void broadcastUpdate(String action) {
		sContext.sendBroadcast(new Intent(action));
	}

	public static final String Attr_UUID = "UUID";
	public static final String Attr_SoftwareVersion = "SoftwareVersion";

	public static void broadcastUpdate(String action, BluetoothGattCharacteristic characteristic) {
		Intent i = new Intent(action);

		if (CharacteristicUUID_SN.equals(characteristic.getUuid())) {
			byte[] response = characteristic.getValue();
			i.putExtra(EXTRA_DATA, new String(response));
		} else if (CharacteristicUUID_SoftwareVersion.equals(characteristic.getUuid())) {
			// ...
		}

		sContext.sendBroadcast(i);
	}
}
