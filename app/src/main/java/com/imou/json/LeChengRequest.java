package com.imou.json;

public class LeChengRequest {
    protected String id;
    protected SystemBean system;
    protected ParamsBean params;

    public SystemBean getSystem() {
        return system;
    }

    public void setSystem(SystemBean system) {
        this.system = system;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ParamsBean getParams() {
        return params;
    }

    public void setParams(ParamsBean params) {
        this.params = params;
    }

    public static class SystemBean {
        private String ver;
        private String sign;
        private String appId;
        private String time;
        private String nonce;

        public String getVer() {
            return ver;
        }

        public void setVer(String ver) {
            this.ver = ver;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getNonce() {
            return nonce;
        }

        public void setNonce(String nonce) {
            this.nonce = nonce;
        }
    }

    public static class ParamsBean {

    }

}
