package com.imou.json;

public class UpgradeDeviceRequest extends LeChengRequest {
    public static class Params extends LeChengRequest.ParamsBean {
        private String token;
        private String deviceId;

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

        @Override
        public String toString() {
            return "Params{" +
                    "token='" + token + '\'' +
                    ", deviceId='" + deviceId + '\'' +
                    '}';
        }
    }
}
