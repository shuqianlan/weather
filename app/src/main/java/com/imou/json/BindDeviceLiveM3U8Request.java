package com.imou.json;

public class BindDeviceLiveM3U8Request extends LeChengRequest {
    public static class Params extends LeChengRequest.ParamsBean {
        private String token;
        private String deviceId;
        private String channelId;
        private String liveMode;
        private int streamId;

        public void setToken(String token) {
            this.token = token;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public void setChannelId(String channelId) {
            this.channelId = channelId;
        }

        public void setLiveMode(String liveMode) {
            this.liveMode = liveMode;
        }

        public void setStreamId(int streamId) {
            this.streamId = streamId;
        }

        @Override
        public String toString() {
            return "Params{" +
                    "token='" + token + '\'' +
                    ", deviceId='" + deviceId + '\'' +
                    ", channelId='" + channelId + '\'' +
                    ", liveMode='" + liveMode + '\'' +
                    ", streamId=" + streamId +
                    '}';
        }
    }
}
