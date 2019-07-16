package com.imou.json;

public class UserTokenResponse {

    protected String id;
    protected UserTokenResult result;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserTokenResult getResult() {
        return result;
    }

    public void setResult(UserTokenResult result) {
        this.result = result;
    }

    public static class UserTokenResult{
        private UserTokenResultData data;
        private String code;
        private String msg;

        public UserTokenResultData getData() {
            return data;
        }

        public void setData(UserTokenResultData data) {
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
            return "UserTokenResult{" +
                    "data=" + data +
                    ", code='" + code + '\'' +
                    ", msg='" + msg + '\'' +
                    '}';
        }
    }

    public static class UserTokenResultData{
        private String userToken;
        private long expiredTime;

        public String getUserToken() {
            return userToken;
        }

        public void setUserToken(String userToken) {
            this.userToken = userToken;
        }

        public long getExpiredTime() {
            return expiredTime;
        }

        public void setExpiredTime(long expiredTime) {
            this.expiredTime = expiredTime;
        }

        @Override
        public String toString() {
            return "UserTokenResultData{" +
                    "userToken='" + userToken + '\'' +
                    ", expiredTime=" + expiredTime +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "UserTokenResponse{" +
                "id='" + id + '\'' +
                ", result=" + result +
                '}';
    }
}
