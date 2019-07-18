package com.imou.json;

import com.google.gson.annotations.JsonAdapter;

@JsonAdapter(AccessTokenResponseTypeAdapter.class)
public class AccessTokenResponse extends LeChengResponse<AccessTokenResponse.ResultData> {
    public static class ResultData {
        private String accessToken;
        private long expireTime;

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public long getExpireTime() {
            return expireTime;
        }

        public void setExpireTime(long expireTime) {
            this.expireTime = expireTime;
        }

        @Override
        public String toString() {
            return "ResultData{" +
                    "accessToken='" + accessToken + '\'' +
                    ", expiredTime=" + expireTime +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "AccessTokenResponse{" +
                "id='" + id + '\'' +
                ", result=" + result +
                '}';
    }
}
