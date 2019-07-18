package com.gson_demo;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.List;

public class DevicesListTypeAdapter extends TypeAdapter<List<DevicesResponse.DeviceBean>> {
    @Override
    public void write(JsonWriter out, List<DevicesResponse.DeviceBean> value) throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }

        out.beginArray();
        for (DevicesResponse.DeviceBean bean:value) {
            out.beginObject();
            out.name("uuid").value(bean.getUuid());
            out.name("name").value(bean.getName());
            out.name("clsType").value(bean.getClsType());
            out.name("heatTs").value(bean.getHeatTs());
            out.endObject();
        }
        out.endArray();
    }

    @Override
    public List<DevicesResponse.DeviceBean> read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }
        return new Gson().fromJson(in, new TypeToken<List<DevicesResponse.DeviceBean>>() {}.getType());
    }
}
