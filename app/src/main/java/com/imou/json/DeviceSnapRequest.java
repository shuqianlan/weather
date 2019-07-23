package com.imou.json;

public class DeviceSnapRequest extends LeChengRequest {
    public static class Params extends LeChengRequest.ParamsBean {
        private String token;
        private String deviceId;
        private String channelId;
        private boolean encrypt;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public String getChannelId() {
            return channelId;
        }

        public void setChannelId(String channelId) {
            this.channelId = channelId;
        }

        public boolean isEncrypt() {
            return encrypt;
        }

        public void setEncrypt(boolean encrypt) {
            this.encrypt = encrypt;
        }

        @Override
        public String toString() {
            return "Params{" +
                    "token='" + token + '\'' +
                    ", deviceId='" + deviceId + '\'' +
                    ", channelId='" + channelId + '\'' +
                    ", encrypt=" + encrypt +
                    '}';
        }
    }
}
