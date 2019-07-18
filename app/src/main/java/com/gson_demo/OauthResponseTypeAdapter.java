package com.gson_demo;

import android.util.Log;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class OauthResponseTypeAdapter extends TypeAdapter<OauthResponse.UserToken> {
    @Override
    public void write(JsonWriter out, OauthResponse.UserToken value) throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }

        out.beginObject()
                .name("userToken").value(value.getUserToken())
                .name("expiredTime").value(value.getExpiredTime())
                .endObject();
    }

    @Override
    public OauthResponse.UserToken read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }

        OauthResponse.UserToken token = new OauthResponse.UserToken();
        in.beginObject();

        while (in.hasNext()) {
            switch (in.nextName()) {
                case "userToken":
                    token.setUserToken(in.nextString());
                    break;
                case "expiredTime":
                    token.setExpiredTime(in.nextLong());
                    break;
            }
        }
        in.endObject();

        Log.d("UserToken", "read: token ");
        return token;
    }
}
