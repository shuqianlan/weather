package com.ilifesmart.ble;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.UUID;

public class BluetoothGattInstance extends BluetoothGattCallback {

	private Context mContext;

	public static final UUID CharacteristicUUID_SN = parseUUIDFrom(new byte[]{(byte) 0x25, (byte) 0x2A});
	public static final UUID CharacteristicUUID_SoftwareVersion = parseUUIDFrom(new byte[]{(byte) 0x28, (byte) 0x2A});

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

	public BluetoothGattInstance(Context context) {
		mContext = context;
	}

	@Override
	public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
		if (newState == BluetoothProfile.STATE_CONNECTED) {
			broadcastUpdate(ACTION_GATT_CONNECTED);
			gatt.discoverServices(); // ASync
		} else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
			broadcastUpdate(ACTION_GATT_DISCONNECTED);
		}
	}

	@Override
	public void onServicesDiscovered(BluetoothGatt gatt, int status) {
		if (status == BluetoothGatt.GATT_SUCCESS) {
			broadcastUpdate(ACTION_GATT_SERVICES_DISCOVERED);
		}
	}

	@Override
	public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
		if (status == BluetoothGatt.GATT_SUCCESS) {
			broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);
		}
	}

	@Override
	public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
		super.onCharacteristicWrite(gatt, characteristic, status);
	}

	@Override
	public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
		super.onCharacteristicChanged(gatt, characteristic);
	}

	public void broadcastUpdate(String action) {
		mContext.sendBroadcast(new Intent(action));
	}

	public static final String Attr_UUID = "UUID";
	public static final String Attr_SoftwareVersion = "SoftwareVersion";

	public void broadcastUpdate(String action, BluetoothGattCharacteristic characteristic) {
		Intent i = new Intent(action);

		Log.d("Instance", "broadcastUpdate: UUID " + characteristic.getUuid());
		if (CharacteristicUUID_SN.equals(characteristic.getUuid())) {
			byte[] response = characteristic.getValue();
			i.putExtra(EXTRA_DATA, new String(response));
		} else if (CharacteristicUUID_SoftwareVersion.equals(characteristic.getUuid())) {
			// ...
		}

		mContext.sendBroadcast(i);
	}

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
}
