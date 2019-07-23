package com.imou.json;

public class ControlMovePTZRequest extends LeChengRequest {
    public static class ControlMovePTZParams extends LeChengRequest.ParamsBean {
        private String token;
        private String deviceId;
        private String channelId;
        private String operation;
        private long duration; // ms

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

        public String getOperation() {
            return operation;
        }

        public void setOperation(String operation) {
            this.operation = operation;
        }

        public long getDuration() {
            return duration;
        }

        public void setDuration(long duration) {
            this.duration = duration;
        }

        @Override
        public String toString() {
            return "ControlMovePTZParams{" +
                    "token='" + token + '\'' +
                    ", deviceId='" + deviceId + '\'' +
                    ", channelId='" + channelId + '\'' +
                    ", operation='" + operation + '\'' +
                    ", duration=" + duration +
                    '}';
        }
    }


}
