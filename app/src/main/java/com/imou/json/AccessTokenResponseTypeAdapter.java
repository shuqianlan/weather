package com.imou.json;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class AccessTokenResponseTypeAdapter extends TypeAdapter<AccessTokenResponse> {
    @Override
    public void write(JsonWriter out, AccessTokenResponse value) throws IOException {

    }

    @Override
    public AccessTokenResponse read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }

        AccessTokenResponse response = new AccessTokenResponse();
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

    private LeChengResponse.ResultBean<AccessTokenResponse.ResultData> getResultBean(JsonReader in) {
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

    private AccessTokenResponse.ResultData getResultData(JsonReader in) {
        AccessTokenResponse.ResultData data = new AccessTokenResponse.ResultData();
        try {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            while (in.hasNext()) {
                switch (in.nextName()) {
                    case "accessToken":
                        data.setAccessToken(in.nextString());
                        break;
                    case "expireTime":
                        data.setExpireTime(in.nextLong());
                        break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return data;
    }
}
