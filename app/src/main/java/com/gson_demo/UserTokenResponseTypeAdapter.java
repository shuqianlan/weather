package com.gson_demo;

import android.util.Log;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.imou.json.LeChengResponse;
import com.imou.json.UserTokenResponse;

import java.io.IOException;

public class UserTokenResponseTypeAdapter extends TypeAdapter<UserTokenResponse> {
    @Override
    public void write(JsonWriter out, UserTokenResponse value) throws IOException { }

    @Override
    public UserTokenResponse read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }

        UserTokenResponse response = new UserTokenResponse();
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
                    response.setResult(getResultBean(in));
                    in.endObject();
                    break;
            }
        }
        in.endObject();

        return response;
    }

    private LeChengResponse.ResultBean<UserTokenResponse.UserTokenResultData> getResultBean(JsonReader in) {
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

    private UserTokenResponse.UserTokenResultData getResultData(JsonReader in) {
        UserTokenResponse.UserTokenResultData data = new UserTokenResponse.UserTokenResultData();
        try {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            while (in.hasNext()) {
                switch (in.nextName()) {
                    case "userToken":
                        data.setUserToken(in.nextString());
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
