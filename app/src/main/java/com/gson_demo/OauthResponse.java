package com.gson_demo;

import com.google.gson.annotations.JsonAdapter;

public class OauthResponse extends Response<OauthResponse.UserToken> {

    @JsonAdapter(OauthResponseTypeAdapter.class)
    public static class UserToken {
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
            return "UserToken{" +
                    "userToken='" + userToken + '\'' +
                    ", expiredTime=" + expiredTime +
                    '}';
        }
    }
}
