package com.imou.json;

import android.util.Log;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DeviceListTypeAdapter extends TypeAdapter<DeviceListResponse> {
    @Override
    public void write(JsonWriter out, DeviceListResponse value) throws IOException { }

    @Override
    public DeviceListResponse read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }

        DeviceListResponse response = new DeviceListResponse();
        in.beginObject();
        while (in.hasNext()) {
            String name = in.nextName();
            Log.d("BBBB", "read: name " + name);
            switch (name) {
                case "id":
                    response.setId(in.nextString());
                    break;
                case "result":
                    in.beginObject();
                    Log.d("BBBB", "read: result ================= ");
                    response.setResult(getResult(in));
                    in.endObject();
                    break;
            }
        }
        in.endObject();

        return response;
    }

    private LeChengResponse.ResultBean<DeviceListResponse.DeviceListResultData> getResult(JsonReader in) {
        LeChengResponse.ResultBean result = new LeChengResponse.ResultBean();
        try {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }

            while (in.hasNext()) {
                switch (in.nextName()) {
                    case "code":
                        result.setCode(in.nextString());
                        break;
                    case "msg":
                        result.setMsg(in.nextString());
                        break;
                    case "data":
                        Log.d("BBBB", "getResult: data");
                        in.beginObject();
                        result.setData(getResultDatas(in));
                        in.endObject();
                        break;
                }
            }
        } catch(Exception ex) {
        	ex.printStackTrace();
        }
        return result;
    }

    private DeviceListResponse.DeviceListResultData getResultDatas(JsonReader in) {
        DeviceListResponse.DeviceListResultData data = new DeviceListResponse.DeviceListResultData();
        List<DeviceListResponse.DeviceListResultData.DeviceListBean> devices = new ArrayList<>();

        try {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }

            while (in.hasNext()) {
                switch (in.nextName()) {
                    case "count":
                        data.setCount(in.nextInt());
                        break;
                    case "devices":
                        Log.d("BBBB", "getResultDatas: devices");
                        in.beginArray();
                        while(in.hasNext()) {
                            devices.add(getDevice(in));
                        }
                        data.setDevices(devices);
                        in.endArray();
                        break;
                }
            }
        } catch(Exception ex) {
        	ex.printStackTrace();
        }

        return data;
    }

    private DeviceListResponse.DeviceListResultData.DeviceListBean getDevice(JsonReader in) {
        DeviceListResponse.DeviceListResultData.DeviceListBean bean = new DeviceListResponse.DeviceListResultData.DeviceListBean();
        List<DeviceListResponse.DeviceListResultData.DeviceListBean.DeviceListBeanChannel> channels = new ArrayList<>();
        try {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }

            in.beginObject();
            while (in.hasNext()) {
                switch (in.nextName()) {
                    case "deviceId":
                        bean.setDeviceId(in.nextString());
                        break;
                    case "status":
                        bean.setStatus(in.nextString());
                        break;
                    case "baseline":
                        bean.setBaseline(in.nextString());
                        break;
                    case "deviceModel":
                        bean.setDeviceModel(in.nextString());
                        break;
                    case "deviceCatalog":
                        bean.setDeviceCatalog(in.nextString());
                        break;
                    case "brand":
                        bean.setBrand(in.nextString());
                        break;
                    case "version":
                        bean.setVersion(in.nextString());
                        break;
                    case "name":
                        bean.setName(in.nextString());
                        break;
                    case "ability":
                        bean.setAbility(in.nextString());
                        break;
                    case "canBeUpgrade":
                        bean.setCanBeUpgrade(in.nextBoolean());
                        break;
                    case "appId":
                        bean.setAppId(in.nextString());
                        break;
                    case "platForm":
                        bean.setPlatForm(in.nextInt());
                        break;
                    case "streamPort":
                        bean.setStreamPort(in.nextInt());
                        break;
                    case "devLoginPassword":
                        bean.setDevLoginPassword(in.nextString());
                        break;
                    case "channelNum":
                        bean.setChannelNum(in.nextInt());
                        break;
                    case "encryptMode":
                        bean.setEncryptMode(in.nextInt());
                        break;
                    case "devLoginName":
                        bean.setDevLoginName(in.nextString());
                        break;
                    case "channels":
                        Log.d("BBBB", "getDevices: channels");
                        in.beginArray();
                        while (in.hasNext()) {
                            channels.add(getChannel(in));
                        }
                        bean.setChannels(channels);
                        in.endArray();
                        break;
                    default:
                        in.skipValue();
                }

            }
            in.endObject();

        } catch(Exception ex) {
        	ex.printStackTrace();
        }
        return bean;
    }

    private DeviceListResponse.DeviceListResultData.DeviceListBean.DeviceListBeanChannel getChannel(JsonReader in) {
        DeviceListResponse.DeviceListResultData.DeviceListBean.DeviceListBeanChannel channel = new DeviceListResponse.DeviceListResultData.DeviceListBean.DeviceListBeanChannel();

        try {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }

            in.beginObject();
            while (in.hasNext()) {
                switch (in.nextName()) {
                    case "channelId":
                        channel.setChannelId(in.nextInt());
                        break;
                    case "channelName":
                        channel.setChannelName(in.nextString());
                        break;
                    case "channelOnline":
                        channel.setChannelOnline(in.nextBoolean());
                        break;
                    case "channelPicUrl":
                        channel.setChannelPicUrl(in.nextString());
                        break;
                    case "alarmStatus":
                        channel.setAlarmStatus(in.nextInt());
                        break;
                    case "csStatus":
                        channel.setCsStatus(in.nextInt());
                        break;
                    case "shareStatus":
                        channel.setShareStatus(in.nextBoolean());
                        break;
                    case "channelAbility":
                        channel.setChannelAbility(in.nextString());
                        break;
                    default:
                        in.skipValue();
                }
            }
            in.endObject();
        } catch(Exception ex) {
        	ex.printStackTrace();
        }

        return channel;
    }
}
