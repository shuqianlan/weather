package com.imou.json;

public class UserBindSms extends LeChengRequest {

    @Override
    public ParamsBean getParams() {
        return params;
    }

    @Override
    public void setParams(ParamsBean params) {
        this.params = params;
    }

    public static class UserBindSmsParams extends LeChengRequest.ParamsBean {
        private String phone;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }
}
