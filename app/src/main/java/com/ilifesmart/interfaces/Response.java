package com.ilifesmart.interfaces;

import java.io.IOException;

public abstract class Response {
    public void onFailure(String msg) {

    }

    public void onFailure(IOException e) {

    }

    public abstract void onSuccess(String msg);
}
