package com.ilifesmart.thread;

import android.view.View;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;

public class TestModule {
    private long age;
    private long doorNum;
    private long streetNum;

    private final View.OnClickListener listener;

    public synchronized long getAge() {
        return age;
    }

    public void setAge(long age) {
        synchronized (this) {
            this.age = age;
        }
    }

    public TestModule() {
        listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
    }

    public View.OnClickListener getInstance() {
        TestModule module = new TestModule();
        return module.listener;
    }

    //
    private static ThreadLocal<Connection> connectionHolder = new ThreadLocal<Connection>() {
        @Override
        protected Connection initialValue() {
            try {
                return DriverManager.getConnection("XXXX");
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return null;
        }
    };

    public static Connection getConnection() {
        return connectionHolder.get();
    }
}
