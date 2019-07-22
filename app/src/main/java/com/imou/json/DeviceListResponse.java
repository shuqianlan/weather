package com.imou.json;

import com.google.gson.annotations.JsonAdapter;

import java.util.List;

@JsonAdapter(DeviceListTypeAdapter.class)
public class DeviceListResponse extends LeChengResponse<DeviceListResponse.DeviceListResultData> {

    public static class DeviceListResultData {
        private int count;
        private List<DeviceListBean> devices;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<DeviceListBean> getDevices() {
            return devices;
        }

        public void setDevices(List<DeviceListBean> devices) {
            this.devices = devices;
        }

        @Override
        public String toString() {
            return "DeviceListResultData{" +
                    "count=" + count +
                    ", devices=" + devices +
                    '}';
        }

        public static class DeviceListBean {
            private String brand;
            private List<DeviceListBeanChannel> channels;
            private int streamPort;
            private boolean canBeUpgrade;
            private String status;
            private String devLoginPassword;
            private String deviceCatalog;
            private String baseline;
            private String appId;
            private String deviceModel;
            private String deviceId;
            private int channelNum;
            private String version;
            private String ability;
            private String name;
            public int encryptMode;
            public String devLoginName = "";
            private int platForm;

            public int getStreamPort() {
                return streamPort;
            }

            public void setStreamPort(int streamPort) {
                this.streamPort = streamPort;
            }

            public String getDevLoginPassword() {
                return devLoginPassword;
            }

            public void setDevLoginPassword(String devLoginPassword) {
                this.devLoginPassword = devLoginPassword;
            }

            public int getChannelNum() {
                return channelNum;
            }

            public void setChannelNum(int channelNum) {
                this.channelNum = channelNum;
            }

            public int getEncryptMode() {
                return encryptMode;
            }

            public void setEncryptMode(int encryptMode) {
                this.encryptMode = encryptMode;
            }

            public String getDevLoginName() {
                return devLoginName;
            }

            public void setDevLoginName(String devLoginName) {
                this.devLoginName = devLoginName;
            }

            public String getDeviceId() {
                return deviceId;
            }

            public void setDeviceId(String deviceId) {
                this.deviceId = deviceId;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
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

            public String getDeviceCatalog() {
                return deviceCatalog;
            }

            public void setDeviceCatalog(String deviceCatalog) {
                this.deviceCatalog = deviceCatalog;
            }

            public String getBrand() {
                return brand;
            }

            public void setBrand(String brand) {
                this.brand = brand;
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

            public String getAppId() {
                return appId;
            }

            public void setAppId(String appId) {
                this.appId = appId;
            }

            public int getPlatForm() {
                return platForm;
            }

            public void setPlatForm(int platForm) {
                this.platForm = platForm;
            }

            public List<DeviceListBeanChannel> getChannels() {
                return channels;
            }

            public void setChannels(List<DeviceListBeanChannel> channels) {
                this.channels = channels;
            }

            @Override
            public String toString() {
                return "DeviceListBean{" +
                        "brand='" + brand + '\'' +
                        ", channels=" + channels +
                        ", streamPort=" + streamPort +
                        ", canBeUpgrade=" + canBeUpgrade +
                        ", status='" + status + '\'' +
                        ", devLoginPassword='" + devLoginPassword + '\'' +
                        ", deviceCatalog='" + deviceCatalog + '\'' +
                        ", baseline='" + baseline + '\'' +
                        ", appId='" + appId + '\'' +
                        ", deviceModel='" + deviceModel + '\'' +
                        ", deviceId='" + deviceId + '\'' +
                        ", channelNum=" + channelNum +
                        ", version='" + version + '\'' +
                        ", ability='" + ability + '\'' +
                        ", name='" + name + '\'' +
                        ", encryptMode=" + encryptMode +
                        ", devLoginName='" + devLoginName + '\'' +
                        ", platForm=" + platForm +
                        '}';
            }

            public static class DeviceListBeanChannel {
                private int channelId;
                private String channelName;
                private boolean channelOnline;
                private String channelPicUrl;
                private int alarmStatus;
                private int csStatus;
                private boolean shareStatus;
                private String channelAbility;

                public String getChannelAbility() {
                    return channelAbility;
                }

                public void setChannelAbility(String channelAbility) {
                    this.channelAbility = channelAbility;
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

                public int getAlarmStatus() {
                    return alarmStatus;
                }

                public void setAlarmStatus(int alarmStatus) {
                    this.alarmStatus = alarmStatus;
                }

                public int getCsStatus() {
                    return csStatus;
                }

                public void setCsStatus(int csStatus) {
                    this.csStatus = csStatus;
                }

                public boolean isShareStatus() {
                    return shareStatus;
                }

                public void setShareStatus(boolean shareStatus) {
                    this.shareStatus = shareStatus;
                }

                @Override
                public String toString() {
                    return "DeviceListBeanChannel{" +
                            "channelId=" + channelId +
                            ", channelName='" + channelName + '\'' +
                            ", channelOnline='" + channelOnline + '\'' +
                            ", channelPicUrl='" + channelPicUrl + '\'' +
                            ", alarmStatus=" + alarmStatus +
                            ", csStatus=" + csStatus +
                            ", shareStatus=" + shareStatus +
                            ", channelAbility=" + channelAbility +
                            '}';
                }
            }
        }
    }

    @Override
    public String toString() {
        return "DeviceListResponse{" +
                "id='" + id + '\'' +
                ", result=" + result +
                '}';
    }
}
