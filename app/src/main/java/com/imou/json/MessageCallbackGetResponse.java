package com.imou.json;

public class MessageCallbackGetResponse extends LeChengResponse<MessageCallbackGetResponse.Data> {
    public static class Data {
        private String status;
        private String callbackUrl;
        private String callbackFlag;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCallbackUrl() {
            return callbackUrl;
        }

        public void setCallbackUrl(String callbackUrl) {
            this.callbackUrl = callbackUrl;
        }

        public String getCallbackFlag() {
            return callbackFlag;
        }

        public void setCallbackFlag(String callbackFlag) {
            this.callbackFlag = callbackFlag;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "status='" + status + '\'' +
                    ", callbackUrl='" + callbackUrl + '\'' +
                    ", callbackFlag='" + callbackFlag + '\'' +
                    '}';
        }
    }
}
