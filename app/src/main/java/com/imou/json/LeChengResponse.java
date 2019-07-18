package com.imou.json;

import android.text.TextUtils;

public class LeChengResponse<T> {
    protected String id;
    protected ResultBean result;

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

    public static class ResultBean<T> {
        private String code;
        private String msg;
        private T data;

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

        public T getData() {
            return data;
        }

        public void setData(T data) {
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

    public boolean isSuccess() {
        return !TextUtils.isEmpty(result.code);
    }

    @Override
    public String toString() {
        return "LeChengResponse{" +
                "id='" + id + '\'' +
                ", result=" + result +
                '}';
    }
}
