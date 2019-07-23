package com.imou.json;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class RecoverSDCardTypeAdapter extends TypeAdapter<RecoverSDCardResponse> {
    @Override
    public void write(JsonWriter out, RecoverSDCardResponse value) throws IOException {

    }

    @Override
    public RecoverSDCardResponse read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }

        RecoverSDCardResponse response = new RecoverSDCardResponse();
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

    private LeChengResponse.ResultBean<RecoverSDCardResponse.Data> getResultBean(JsonReader in) {
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

    private RecoverSDCardResponse.Data getResultData(JsonReader in) {
        RecoverSDCardResponse.Data data = new RecoverSDCardResponse.Data();
        try {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            while (in.hasNext()) {
                switch (in.nextName()) {
                    case "Result":
                        data.setResult(in.nextString());
                        break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return data;
    }
}
