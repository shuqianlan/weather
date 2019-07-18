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
                        in.beginArray();
                        data.setDevices(getDevices(in));
                        in.endArray();
                        break;
                }
            }
        } catch(Exception ex) {
        	ex.printStackTrace();
        }

        return data;
    }

    private List<DeviceListResponse.DeviceListResultData.DeviceListBean> getDevices(JsonReader in) {
        List<DeviceListResponse.DeviceListResultData.DeviceListBean> devices = new ArrayList<>();

        try {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return devices;
            }

            while (in.hasNext()) {
                in.beginObject();
                DeviceListResponse.DeviceListResultData.DeviceListBean bean = new DeviceListResponse.DeviceListResultData.DeviceListBean();
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
                    case "channels":
                        in.beginArray();
                        bean.setChannels(getChannels(in));
                        in.endArray();
                        break;
                }
                devices.add(bean);
                in.endObject();
            }
        } catch(Exception ex) {
        	ex.printStackTrace();
        }
        return devices;
    }

    private List<DeviceListResponse.DeviceListResultData.DeviceListBean.DeviceListBeanChannel> getChannels(JsonReader in) {
        List<DeviceListResponse.DeviceListResultData.DeviceListBean.DeviceListBeanChannel> channels = new ArrayList<>();

        try {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return channels;
            }

            while (in.hasNext()) {
                in.beginObject();
                DeviceListResponse.DeviceListResultData.DeviceListBean.DeviceListBeanChannel channel = new DeviceListResponse.DeviceListResultData.DeviceListBean.DeviceListBeanChannel();
                switch (in.nextName()) {
                    case "channelId":
                        channel.setChannelId(in.nextInt());
                        break;
                    case "channelName":
                        channel.setChannelName(in.nextString());
                        break;
                    case "channelOnline":
                        channel.setChannelOnline(in.nextString());
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
                }
                channels.add(channel);
                in.endObject();
            }
        } catch(Exception ex) {
        	ex.printStackTrace();
        }

        return channels;
    }
}
