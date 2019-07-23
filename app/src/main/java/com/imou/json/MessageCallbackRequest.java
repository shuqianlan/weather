package com.imou.json;

public class MessageCallbackRequest extends LeChengRequest {
    public static class Params extends LeChengRequest.ParamsBean {
        private String token;
        private String status="on"; // 是否订阅消息
        private String callbackUrl="http://127.0.0.1";

        /*
         * 回调标示,","隔开
         * alarm: 设备动检报警推送
         * deviceStatus: 设备上下线推送.
         * numberstat：客流数据相关推送；
         * faceAnalysis：人脸相关事件推送
         * status为on时必填
         * */
        private String callbackFlag = "alarm,deviceStatus";

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getCallbackFlag() {
            return callbackFlag;
        }

        public void setCallbackFlag(String callbackFlag) {
            this.callbackFlag = callbackFlag;
        }

        public String getStatus() {
            return status;
        }

        public String getCallbackUrl() {
            return callbackUrl;
        }

        @Override
        public String toString() {
            return "Params{" +
                    "token='" + token + '\'' +
                    ", status='" + status + '\'' +
                    ", callbackUrl='" + callbackUrl + '\'' +
                    ", callbackFlag='" + callbackFlag + '\'' +
                    '}';
        }
    }
}
