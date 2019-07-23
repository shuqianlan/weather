package com.imou.json;

public class MessageCallBackGetRequest extends LeChengRequest {
    public static class Params extends ParamsBean {
        private String token;

        public void setToken(String token) {
            this.token = token;
        }

        @Override
        public String toString() {
            return "Params{" +
                    "token='" + token + '\'' +
                    '}';
        }
    }
}
