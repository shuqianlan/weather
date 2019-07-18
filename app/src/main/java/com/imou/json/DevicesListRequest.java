package com.imou.json;

public class DevicesListRequest extends LeChengRequest {
    public static class DeviceListParams extends ParamsBean {
        private String token;
        private String queryRange;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getQueryRange() {
            return queryRange;
        }

        public void setQueryRange(String queryRange) {
            this.queryRange = queryRange;
        }

        @Override
        public String toString() {
            return "DeviceListParams{" +
                    "token='" + token + '\'' +
                    ", queryRange='" + queryRange + '\'' +
                    '}';
        }
    }
}
