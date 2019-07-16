package com.imou.json;

public class DeviceOnline extends LeChengRequest {
    public static class DeviceOnlineParams extends LeChengRequest.ParamsBean {
        private String deviceId;
        private String token;

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        @Override
        public String toString() {
            return "DeviceOnlineParams{" +
                    "deviceId='" + deviceId + '\'' +
                    ", token='" + token + '\'' +
                    '}';
        }
    }
}
