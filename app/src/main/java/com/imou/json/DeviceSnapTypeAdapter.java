package com.imou.json;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class DeviceSnapTypeAdapter extends TypeAdapter<DeviceSnapResponse> {
    @Override
    public void write(JsonWriter out, DeviceSnapResponse value) throws IOException {

    }

    @Override
    public DeviceSnapResponse read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }

        DeviceSnapResponse response = new DeviceSnapResponse();
        in.beginObject();
        while (in.hasNext()) {
            switch(in.nextName()) {
                case "id":
                    response.setId(in.nextString());
                    break;
                case "result":
                    in.beginObject();
                    response.setResult(getResultBean(in));
                    in.endObject();
                    break;
            }
        }

        in.endObject();
        return response;
    }

    private LeChengResponse.ResultBean<DeviceSnapResponse.Data> getResultBean(JsonReader in) {
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
                        result.setData(getResultData(in));
                        in.endObject();
                        break;
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return result;
    }

    private DeviceSnapResponse.Data getResultData(JsonReader in) {
        DeviceSnapResponse.Data data = new DeviceSnapResponse.Data();
        try {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            while (in.hasNext()) {
                switch (in.nextName()) {
                    case "url":
                        data.setUrl(in.nextString());
                        break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return data;
    }
}
