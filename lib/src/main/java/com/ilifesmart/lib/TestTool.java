package com.ilifesmart.lib;

import com.ilifesmart.annotation.JieCha;

import java.lang.reflect.Method;

public class TestTool {

    public static void testNoBus() {
        NoBug none = new NoBug();
        Class clazz = none.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        StringBuilder log = new StringBuilder();
        int errNum = 0;

        for(Method method: methods) {
            if (method.isAnnotationPresent(JieCha.class)) {
                try {
                    method.setAccessible(true);
                    method.invoke(none, null);
                } catch (Exception e) {
                    errNum++;
                    log.append(method.getName())
                            .append(" ")
                            .append("has error\r\n caused by ")
                            .append(e.getCause().getClass().getSimpleName())
                            .append("\r\n")
                            .append(e.getCause().getMessage())
                            .append("\r\n");
                }
            }
        }

        log.append(clazz.getSimpleName())
            .append("has ").append(errNum).append(" error");
        System.out.println("Log: " + log);

    }
}
