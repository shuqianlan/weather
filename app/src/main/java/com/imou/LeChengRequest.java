package com.imou;

public class LeChengRequest {

    /**
     * system : {"ver":"1.0","sign":"0a9f324dc430eace4f4fd1b06738cdaa","appId":"lc91fa6e24ff4b4e99","time":"1404443389","nonce":"169431af-9750-408d-a8ae-eb7d07f93c4e"}
     * params : {"deviceId":"123456789","accessToken":"f88c4dbb354711c9bf6597a4987dce90"}
     * id : 38e3543b-4a5d-4a55-b25d-d9018af9b222
     */

    private SystemBean system;
    private ParamsBean params;
    private String id;

    public SystemBean getSystem() {
        return system;
    }

    public void setSystem(SystemBean system) {
        this.system = system;
    }

    public ParamsBean getParams() {
        return params;
    }

    public void setParams(ParamsBean params) {
        this.params = params;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static class SystemBean {
        /**
         * ver : 1.0
         * sign : 0a9f324dc430eace4f4fd1b06738cdaa
         * appId : lc91fa6e24ff4b4e99
         * time : 1404443389
         * nonce : 169431af-9750-408d-a8ae-eb7d07f93c4e
         */

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
        /**
         * deviceId : 123456789
         * accessToken : f88c4dbb354711c9bf6597a4987dce90
         */

        private String phone;

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }
}
