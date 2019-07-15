package com.imou.json;

public class UserBind extends LeChengRequest {

    @Override
    public void setParams(ParamsBean params) {
        super.setParams(params);
    }

    @Override
    public ParamsBean getParams() {
        return super.getParams();
    }

    public static class UserBindParams extends LeChengRequest.ParamsBean {
        private String phone;
        private String smsCode;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getSmsCode() {
            return smsCode;
        }

        public void setSmsCode(String smsCode) {
            this.smsCode = smsCode;
        }

        @Override
        public String toString() {
            return "UserBindParams{" +
                    "phone='" + phone + '\'' +
                    ", smsCode='" + smsCode + '\'' +
                    '}';
        }
    }
}
