package com.gson_demo;

public class Response<T> {
    private String id;
    private String msg;
    private T data;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        return "Response{" +
                "id='" + id + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
