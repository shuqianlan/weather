package com.imou.json;

import com.google.gson.stream.JsonReader;
import com.imou.CustomTypeAdapter;

public class MessageCallbackGetResponseTypeAdapter extends CustomTypeAdapter<MessageCallbackGetResponse, MessageCallbackGetResponse.Data> {
    @Override
    protected MessageCallbackGetResponse createResponse() {
        return new MessageCallbackGetResponse();
    }

    @Override
    protected MessageCallbackGetResponse.Data getResultData(JsonReader in) {
        MessageCallbackGetResponse.Data data = new MessageCallbackGetResponse.Data();
        try {
            while (in.hasNext()) {
                switch (in.nextName()) {
                    case "status":
                        data.setStatus(in.nextString());
                        break;
                    case "callbackUrl":
                        data.setCallbackUrl(in.nextString());
                        break;
                    case "callbackFlag":
                        data.setCallbackFlag(in.nextString());
                        break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            data = null;
        }
        return data;
    }
}
