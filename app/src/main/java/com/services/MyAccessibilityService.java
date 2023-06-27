package com.services;

import android.accessibilityservice.AccessibilityButtonController;
import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

public class MyAccessibilityService extends AccessibilityService {
    public static final String TAG = "MyAccessibilityService";

    private AccessibilityButtonController accessibilityButtonController;
    private AccessibilityButtonController
            .AccessibilityButtonCallback accessibilityButtonCallback;
    private boolean mIsAccessibilityButtonAvailable;

    @Override
    public void onCreate() {
        super.onCreate();

        // 激活"触摸浏览"/显示不太重要的视图
        getServiceInfo().flags = AccessibilityServiceInfo.FLAG_REQUEST_TOUCH_EXPLORATION_MODE | AccessibilityServiceInfo.FLAG_INCLUDE_NOT_IMPORTANT_VIEWS;

        Log.d(TAG, "onCreate: ~");
    }

    // 服务启动时
    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            accessibilityButtonController = getAccessibilityButtonController();
            mIsAccessibilityButtonAvailable = accessibilityButtonController.isAccessibilityButtonAvailable();
            if (!mIsAccessibilityButtonAvailable) {
                return;
            }

            AccessibilityServiceInfo serviceInfo = getServiceInfo();
            serviceInfo.flags |= AccessibilityServiceInfo.FLAG_REQUEST_ACCESSIBILITY_BUTTON;
            setServiceInfo(serviceInfo);

            accessibilityButtonCallback = new AccessibilityButtonController.AccessibilityButtonCallback() {
                @Override
                public void onClicked(AccessibilityButtonController controller) {
                    super.onClicked(controller);

                    Log.d(TAG, "onClicked: ");
                }

                @Override
                public void onAvailabilityChanged(AccessibilityButtonController controller, boolean available) {
                    super.onAvailabilityChanged(controller, available);

                    if (controller.equals(accessibilityButtonController)) {
                        mIsAccessibilityButtonAvailable = available;
                    }
                }
            };

            if (accessibilityButtonCallback != null) {
                accessibilityButtonController.registerAccessibilityButtonCallback(accessibilityButtonCallback, null);
            }
        }

        Log.d(TAG, "onServiceConnected: ~");
    }

    // 服务启动时 运行时
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        AccessibilityNodeInfo nodeInfo = event.getSource();

        String pkgName = event.getPackageName().toString();
        int eventType = event.getEventType();

//        if (eventType == AccessibilityEvent.TYPE_VIEW_CLICKED) {
//
//        }
//        performGlobalAction(GLOBAL_ACTION_BACK);
        Log.d(TAG, "onAccessibilityEvent: pkgname " + pkgName + " eventType " + eventType);
        nodeInfo.recycle();
    }

    // 拦截
    @Override
    public void onInterrupt() {

    }

    // 解绑
    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }
}