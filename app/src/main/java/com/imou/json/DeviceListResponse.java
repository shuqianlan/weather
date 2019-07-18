package com.imou.json;

import com.google.gson.annotations.JsonAdapter;
import com.gson_demo.DevicesListTypeAdapter;

import java.util.List;

@JsonAdapter(DevicesListTypeAdapter.class)
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
            private String deviceId;
            private String status;
            private String baseline;
            private String deviceModel;
            private String deviceCatalog;
            private String brand;
            private String version;
            private String name;
            private String ability;
            private boolean canBeUpgrade;
            private String appId;
            private int platForm;
            private List<DeviceListBeanChannel> channels;

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
                        "deviceId='" + deviceId + '\'' +
                        ", status='" + status + '\'' +
                        ", baseline='" + baseline + '\'' +
                        ", deviceModel='" + deviceModel + '\'' +
                        ", deviceCatalog='" + deviceCatalog + '\'' +
                        ", brand='" + brand + '\'' +
                        ", version='" + version + '\'' +
                        ", name='" + name + '\'' +
                        ", ability='" + ability + '\'' +
                        ", canBeUpgrade=" + canBeUpgrade +
                        ", appId='" + appId + '\'' +
                        ", platForm=" + platForm +
                        ", channels=" + channels +
                        '}';
            }

            public static class DeviceListBeanChannel {
                private int channelId;
                private String channelName;
                private String channelOnline;
                private String channelPicUrl;
                private int alarmStatus;
                private int csStatus;
                private boolean shareStatus;

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

                public String getChannelOnline() {
                    return channelOnline;
                }

                public void setChannelOnline(String channelOnline) {
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
