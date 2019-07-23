package com.imou;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.imou.json.LeChengResponse;

import java.io.IOException;

public abstract class CustomTypeAdapter<T extends LeChengResponse,R> extends TypeAdapter<T> {
    @Override
    public void write(JsonWriter out, T value) throws IOException {

    }

    @Override
    public T read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }

        T response = createResponse();
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

    protected abstract T createResponse();
    protected abstract R getResultData(JsonReader in);

    protected LeChengResponse.ResultBean getResultBean(JsonReader in) {
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
                        if (in.peek() == JsonToken.NULL) {
                            in.nextNull();
                        }
                        if (in.hasNext()) {
                            in.beginObject();
                            result.setData(getResultData(in));
                            in.endObject();
                        }
                        break;
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return result;
    }
}
