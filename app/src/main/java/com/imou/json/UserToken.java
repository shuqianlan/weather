package com.imou.json;

public class UserToken extends LeChengResponse<UserToken.UserTokenResultData> {

    /*
    * 由于返回值中Data的类型不确定，此处通过registerTypeAdapter解决.
    * */
    @Override
    public void setResult(UserTokenResultData result) {
        super.setResult(result);
    }

    @Override
    public UserTokenResultData getResult() {
        return super.getResult();
    }

    public static class UserTokenResultData extends LeChengResponse.ResultBean {
        private String userToken;
        private long expireTime;

        public String getUserToken() {
            return userToken;
        }

        public void setUserToken(String userToken) {
            this.userToken = userToken;
        }

        public long getExpireTime() {
            return expireTime;
        }

        public void setExpireTime(long expireTime) {
            this.expireTime = expireTime;
        }

        @Override
        public String toString() {
            return "UserTokenResultData{" +
                    "userToken='" + userToken + '\'' +
                    ", expireTime=" + expireTime +
                    '}';
        }
    }
}
