package com.imou.json;

import java.util.List;

public class DeviceOnlineResponsse {

    protected String id;
    protected UserTokenResponse.UserTokenResult result;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserTokenResponse.UserTokenResult getResult() {
        return result;
    }

    public void setResult(UserTokenResponse.UserTokenResult result) {
        this.result = result;
    }

    public static class Result {
        private DeviceOnlineData data;
        private String code;
        private String msg;

        public DeviceOnlineData getData() {
            return data;
        }

        public void setData(DeviceOnlineData data) {
            this.data = data;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        @Override
        public String toString() {
            return "DeviceOnlineResult{" +
                    "data=" + data +
                    ", code='" + code + '\'' +
                    ", msg='" + msg + '\'' +
                    '}';
        }
    }

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

    @Override
    public String toString() {
        return "DeviceOnlineResponse{" +
                "id='" + id + '\'' +
                ", result=" + result +
                '}';
    }
}
