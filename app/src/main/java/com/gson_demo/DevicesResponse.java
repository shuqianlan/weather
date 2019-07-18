package com.gson_demo;

import java.util.List;

public class DevicesResponse extends Response<List<DevicesResponse.DeviceBean>> {

    public static class DeviceBean {
        private String uuid;
        private String name;
        private String clsType;
        private long heatTs;

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getClsType() {
            return clsType;
        }

        public void setClsType(String clsType) {
            this.clsType = clsType;
        }

        public long getHeatTs() {
            return heatTs;
        }

        public void setHeatTs(long heatTs) {
            this.heatTs = heatTs;
        }

        @Override
        public String toString() {
            return "DeviceBean{" +
                    "uuid='" + uuid + '\'' +
                    ", name='" + name + '\'' +
                    ", clsType='" + clsType + '\'' +
                    ", heatTs=" + heatTs +
                    '}';
        }
    }
}
