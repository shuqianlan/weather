package com.imou.json;

public class BindRtspLiveRequest extends LeChengRequest {
    public static class Params extends ParamsBean {
        private String token;
        private String streamUrl = "http://zxcv";

        public void setToken(String token) {
            this.token = token;
        }

        public String getStreamUrl() {
            return streamUrl;
        }
    }
}
