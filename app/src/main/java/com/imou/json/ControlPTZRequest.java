package com.imou.json;

public class ControlPTZRequest extends LeChengRequest {
    public static class  ControlPTZRequestParams extends LeChengRequest.ParamsBean {
        private String token;
        private String deviceId;
        private String channelId;
        private String operation;
        private double h;
        private double v;
        private double z;
        private String duration;

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

        public double getH() {
            return h;
        }

        public void setH(double h) {
            this.h = h;
        }

        public double getV() {
            return v;
        }

        public void setV(double v) {
            this.v = v;
        }

        public double getZ() {
            return z;
        }

        public void setZ(double z) {
            this.z = z;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        @Override
        public String toString() {
            return "ControlPTZRequestParams{" +
                    "token='" + token + '\'' +
                    ", deviceId='" + deviceId + '\'' +
                    ", channelId='" + channelId + '\'' +
                    ", operation='" + operation + '\'' +
                    ", h=" + h +
                    ", v=" + v +
                    ", z=" + z +
                    ", duration='" + duration + '\'' +
                    '}';
        }
    }
}
