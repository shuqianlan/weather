package com.imou;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import com.ilifesmart.ToolsApplication;

import java.util.UUID;

public class LeChengUtils {
    public static String ApiBaseUrl = "https://";

    public static String generateDeviceUUID() {
        try {
            String androidId = Settings.Secure.getString(ToolsApplication.getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
            if (androidId != null && !"9774d56d682e549c".equals(androidId))
                return UUID.nameUUIDFromBytes(androidId.getBytes("utf8")).toString();
            final String deviceId = null;
            if (deviceId != null)
                return UUID.nameUUIDFromBytes(deviceId.getBytes("utf8")).toString();
            String macAddress = ((WifiManager) ToolsApplication.getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE)).getConnectionInfo().getMacAddress();
            if (macAddress != null)
                return UUID.nameUUIDFromBytes(macAddress.getBytes("utf8")).toString();
        } catch (Exception exp) {
            exp.printStackTrace();
        }

        try {
            String buildId = "35" +
                    Build.BOARD.length() % 10 +
                    Build.BRAND.length() % 10 +
                    Build.DEVICE.length() % 10 +
                    Build.DISPLAY.length() % 10 +
                    Build.HOST.length() % 10 +
                    Build.ID.length() % 10 +
                    Build.MANUFACTURER.length() % 10 +
                    Build.MODEL.length() % 10 +
                    Build.PRODUCT.length() % 10 +
                    Build.TAGS.length() % 10 +
                    Build.TYPE.length() % 10 +
                    Build.USER.length() % 10;
            return UUID.nameUUIDFromBytes(buildId.getBytes("utf8")).toString();
        } catch (Exception exp2) {
            exp2.printStackTrace();
        }
        return null;
    }

}
