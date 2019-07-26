package com.imou.json;

import android.util.Log;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.imou.CustomTypeAdapter;

import java.util.ArrayList;
import java.util.List;

public class AuthedDeviceListResponseTypeAdapter extends CustomTypeAdapter<AuthedDeviceListResponse,AuthedDeviceListResponse.Data> {
    @Override
    protected AuthedDeviceListResponse createResponse() {
        return new AuthedDeviceListResponse();
    }

    @Override
    protected AuthedDeviceListResponse.Data getResultData(JsonReader in) {
        AuthedDeviceListResponse.Data data = new AuthedDeviceListResponse.Data();
        List<AuthedDeviceListResponse.Data.AuthedDevice> devices = new ArrayList<>();

        try {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }

            while (in.hasNext()) {
                switch (in.nextName()) {
                    case "devices":
                        Log.d("BBBB", "getResultDatas: devices");
                        in.beginArray();
                        while(in.hasNext()) {
                            AuthedDeviceListResponse.Data.AuthedDevice device = getDevice(in);
                            Log.d("BBBB", "getResultData: device " + device);
                            devices.add(device);
                        }
                        in.endArray();
                        break;
                }
            }
            data.setDevices(devices);
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        return data;
    }

    public AuthedDeviceListResponse.Data.AuthedDevice getDevice(JsonReader in) {
        AuthedDeviceListResponse.Data.AuthedDevice device = new AuthedDeviceListResponse.Data.AuthedDevice();
        try {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }

            in.beginObject();
            while (in.hasNext()) {
                String name = in.nextName();
                Log.d("BBBB", "getDevice: name " + name);
                switch (name) {
                    case "deviceId":
                        device.setDeviceId(in.nextString());
                        break;
                    case "status":
                        device.setStatus(in.nextInt());
                        break;
                    case "baseline":
                        device.setBaseline(in.nextString());
                        break;
                    case "deviceModel":
                        device.setDeviceModel(in.nextString());
                        break;
                    case "encryptMode":
                        device.setEncryptMode(in.nextInt());
                        break;
                    case "deviceCatalog":
                        device.setDeviceCatalog(in.nextString());
                        break;
                    case "version":
                        device.setVersion(in.nextString());
                        break;
                    case "name":
                        device.setName(in.nextString());
                        break;
                    case "ability":
                        device.setAbility(in.nextString());
                        break;
                    case "canBeUpgrade":
                        device.setCanBeUpgrade(in.nextBoolean());
                        break;
                    case "channelId":
                        device.setChannelId(in.nextInt());
                        break;
                    case "channelName":
                        device.setChannelName(in.nextString());
                        break;
                    case "channelOnline":
                        device.setChannelOnline(in.nextBoolean());
                        break;
                    case "channelPicUrl":
                        device.setChannelPicUrl(in.nextString());
                        break;
                    case "functions":
                        device.setFunctions(in.nextString());
                        break;
                }
            }
            in.endObject();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return device;
    }
}
