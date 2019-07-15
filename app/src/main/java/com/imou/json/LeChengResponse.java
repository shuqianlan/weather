package com.imou.json;

public class LeChengResponse<T extends LeChengResponse.ResultBean> {
    protected String id;
    protected T result;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public static class ResultBean {
        private String code;
        private String msg;
        private Object data;

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

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "ResultBean{" +
                    "code='" + code + '\'' +
                    ", msg='" + msg + '\'' +
                    ", data=" + data +
                    '}';
        }
    }

    public abstract static class ResultDataBean {

    }

    @Override
    public String toString() {
        return "LeChengResponse{" +
                "id='" + id + '\'' +
                ", result=" + result +
                '}';
    }
}
