package com.imou.json;

import java.util.List;

public class Tmp {

    /**
     * id : 123456
     * result : {"data":{"deviceId":"2342sdfl-df323-23","onLine":"0","channels":[{"channelId":0,"onLine":"0"}]},"code":"0","msg":"操作成功"}
     */

    private String id;
    private ResultBean result;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * data : {"deviceId":"2342sdfl-df323-23","onLine":"0","channels":[{"channelId":0,"onLine":"0"}]}
         * code : 0
         * msg : 操作成功
         */

        private DataBean data;
        private String code;
        private String msg;

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public static class DataBean {
            /**
             * deviceId : 2342sdfl-df323-23
             * onLine : 0
             * channels : [{"channelId":0,"onLine":"0"}]
             */

            private String deviceId;
            private String onLine;
            private List<ChannelsBean> channels;

            public String getDeviceId() {
                return deviceId;
            }

            public void setDeviceId(String deviceId) {
                this.deviceId = deviceId;
            }

            public String getOnLine() {
                return onLine;
            }

            public void setOnLine(String onLine) {
                this.onLine = onLine;
            }

            public List<ChannelsBean> getChannels() {
                return channels;
            }

            public void setChannels(List<ChannelsBean> channels) {
                this.channels = channels;
            }

            public static class ChannelsBean {
                /**
                 * channelId : 0
                 * onLine : 0
                 */

                private int channelId;
                private String onLine;

                public int getChannelId() {
                    return channelId;
                }

                public void setChannelId(int channelId) {
                    this.channelId = channelId;
                }

                public String getOnLine() {
                    return onLine;
                }

                public void setOnLine(String onLine) {
                    this.onLine = onLine;
                }
            }
        }
    }
}
