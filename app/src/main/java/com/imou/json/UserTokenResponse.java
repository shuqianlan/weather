package com.imou.json;

import com.google.gson.annotations.JsonAdapter;
import com.gson_demo.UserTokenResponseTypeAdapter;

@JsonAdapter(UserTokenResponseTypeAdapter.class)
public class UserTokenResponse extends LeChengResponse<UserTokenResponse.UserTokenResultData> {

    public static class UserTokenResultData{
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
                    ", expiredTime=" + expireTime +
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
