package com.imou.json;

import com.google.gson.annotations.JsonAdapter;

import java.util.List;

public class DeviceOnlineResponsse extends LeChengResponse<DeviceOnlineResponsse.DeviceOnlineData> {

    @JsonAdapter(DeviceOnlineDataTypeAdapter.class)
    public static class DeviceOnlineData{
        private String deviceId;
        private String onLine;
        private List<DeviceStatusChannel> channels;

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public String getOnLine() {
            return onLine;
        }

        public void setOnLine(String onLine) {
            this.onLine = onLine;
        }

        public List<DeviceStatusChannel> getChannels() {
            return channels;
        }

        public void setChannels(List<DeviceStatusChannel> channels) {
            this.channels = channels;
        }

        @Override
        public String toString() {
            return "DeviceOnlineData{" +
                    "deviceId='" + deviceId + '\'' +
                    ", onLine='" + onLine + '\'' +
                    ", channels=" + channels +
                    '}';
        }

        public static class DeviceStatusChannel {
            private String channelId;
            private String onLine;

            public String getChannelId() {
                return channelId;
            }

            public void setChannelId(String channelId) {
                this.channelId = channelId;
            }

            public String getOnLine() {
                return onLine;
            }

            public void setOnLine(String onLine) {
                this.onLine = onLine;
            }

            @Override
            public String toString() {
                return "DeviceStatusChannel{" +
                        "channelId='" + channelId + '\'' +
                        ", onLine='" + onLine + '\'' +
                        '}';
            }
        }

    }

    @Override
    public String toString() {
        return "DeviceOnlineResponse{" +
                "id='" + id + '\'' +
                ", result=" + result +
                '}';
    }
}
