package com.imou.json;

import com.google.gson.annotations.JsonAdapter;

@JsonAdapter(DeviceSnapTypeAdapter.class)
public class DeviceSnapResponse extends LeChengResponse<DeviceSnapResponse.Data> {
    public static class Data {
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "url='" + url + '\'' +
                    '}';
        }
    }
}
