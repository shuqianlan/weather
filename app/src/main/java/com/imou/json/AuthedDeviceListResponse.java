package com.imou.json;

import com.google.gson.annotations.JsonAdapter;

import java.util.List;

@JsonAdapter(AuthedDeviceListResponseTypeAdapter.class)
public class AuthedDeviceListResponse extends LeChengResponse<AuthedDeviceListResponse.Data> {

    public static class Data {
        private List<AuthedDevice> devices;

        public List<AuthedDevice> getDevices() {
            return devices;
        }

        public void setDevices(List<AuthedDevice> devices) {
            this.devices = devices;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "devices=" + devices +
                    '}';
        }

        public static class AuthedDevice {
            private String deviceId;
            private int status;
            private String baseline;
            private String deviceModel;
            private int encryptMode;
            private String deviceCatalog;
            private String version;
            private String name;
            private String ability;
            private boolean canBeUpgrade;
            private int channelId;
            private String channelName;
            private boolean channelOnline;
            private String channelPicUrl;
            private String functions;

            public String getDeviceId() {
                return deviceId;
            }

            public void setDeviceId(String deviceId) {
                this.deviceId = deviceId;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getBaseline() {
                return baseline;
            }

            public void setBaseline(String baseline) {
                this.baseline = baseline;
            }

            public String getDeviceModel() {
                return deviceModel;
            }

            public void setDeviceModel(String deviceModel) {
                this.deviceModel = deviceModel;
            }

            public int getEncryptMode() {
                return encryptMode;
            }

            public void setEncryptMode(int encryptMode) {
                this.encryptMode = encryptMode;
            }

            public String getDeviceCatalog() {
                return deviceCatalog;
            }

            public void setDeviceCatalog(String deviceCatalog) {
                this.deviceCatalog = deviceCatalog;
            }

            public String getVersion() {
                return version;
            }

            public void setVersion(String version) {
                this.version = version;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAbility() {
                return ability;
            }

            public void setAbility(String ability) {
                this.ability = ability;
            }

            public boolean isCanBeUpgrade() {
                return canBeUpgrade;
            }

            public void setCanBeUpgrade(boolean canBeUpgrade) {
                this.canBeUpgrade = canBeUpgrade;
            }

            public int getChannelId() {
                return channelId;
            }

            public void setChannelId(int channelId) {
                this.channelId = channelId;
            }

            public String getChannelName() {
                return channelName;
            }

            public void setChannelName(String channelName) {
                this.channelName = channelName;
            }

            public boolean isChannelOnline() {
                return channelOnline;
            }

            public void setChannelOnline(boolean channelOnline) {
                this.channelOnline = channelOnline;
            }

            public String getChannelPicUrl() {
                return channelPicUrl;
            }

            public void setChannelPicUrl(String channelPicUrl) {
                this.channelPicUrl = channelPicUrl;
            }

            public String getFunctions() {
                return functions;
            }

            public void setFunctions(String functions) {
                this.functions = functions;
            }

            @Override
            public String toString() {
                return "AuthedDevice{" +
                        "deviceId='" + deviceId + '\'' +
                        ", status=" + status +
                        ", baseline='" + baseline + '\'' +
                        ", deviceModel=" + deviceModel +
                        ", encryptMode='" + encryptMode + '\'' +
                        ", deviceCatalog='" + deviceCatalog + '\'' +
                        ", version='" + version + '\'' +
                        ", name='" + name + '\'' +
                        ", ability='" + ability + '\'' +
                        ", canBeUpgrade=" + canBeUpgrade +
                        ", channelId=" + channelId +
                        ", channelName='" + channelName + '\'' +
                        ", channelOnline='" + channelOnline + '\'' +
                        ", channelPicUrl='" + channelPicUrl + '\'' +
                        ", functions='" + functions + '\'' +
                        '}';
            }
        }


    }
}
