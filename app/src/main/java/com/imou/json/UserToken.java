package com.imou.json;

public class UserToken extends LeChengResponse<UserToken.UserTokenResult> {

    /*
    * 由于返回值中Data的类型不确定，此处通过registerTypeAdapter解决.
    * */
    @Override
    public void setResult(UserTokenResult result) {
        super.setResult(result);
    }

    @Override
    public UserTokenResult getResult() {
        return super.getResult();
    }

    public static class UserTokenResult extends LeChengResponse.ResultBean {
        private UserTokenResultData data;

        @Override
        public UserTokenResultData getData() {
            return data;
        }

        public void setData(UserTokenResultData data) {
            this.data = data;
        }
    }

    public static class UserTokenResultData extends ResultDataBean {
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
}
