package com.imou.json;

import android.text.TextUtils;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.List;

public class DeviceOnlineDataTypeAdapter extends TypeAdapter<DeviceOnlineResponsse.DeviceOnlineData> {
    @Override
    public void write(JsonWriter out, DeviceOnlineResponsse.DeviceOnlineData value) throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }
        out.beginObject();
        out.name("deviceId").value(value.getDeviceId()).
            name("onLine").value(value.getOnLine()).
            name("channels").value(TextUtils.join(";", value.getChannels()));
        out.endObject();

    }

    @Override
    public DeviceOnlineResponsse.DeviceOnlineData read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }

        DeviceOnlineResponsse.DeviceOnlineData data = new DeviceOnlineResponsse.DeviceOnlineData();
        in.beginObject();
        while(in.hasNext()) {
            switch (in.nextName()) {
                case "deviceId":
                    data.setDeviceId(in.nextString());
                    break;
                case "onLine":
                    data.setOnLine(in.nextString());
                    break;
                case "channels":
                    data.setChannels(new Gson().fromJson(in.nextString(), new TypeToken<List<DeviceOnlineResponsse.DeviceOnlineData.DeviceStatusChannel>>() {}.getType()));
                    break;
            }
        }
        in.endObject();
        return data;
    }
}
