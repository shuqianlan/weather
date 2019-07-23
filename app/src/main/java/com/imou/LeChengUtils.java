package com.imou;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.widget.Toast;
import com.ilifesmart.ToolsApplication;
import com.ilifesmart.utils.PersistentMgr;
import com.ilifesmart.weather.R;
import com.imou.json.DeviceListResponse;
import com.imou.json.LeChengResponse;

import java.util.ArrayList;
import java.util.List;
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

    public static List<ChannelInfo> devicesElementToResult(DeviceListResponse.DeviceListResultData.DeviceListBean devElement) {

        List<ChannelInfo> channelList = new ArrayList<ChannelInfo>();

        // 解析绑定通道列表
        List<DeviceListResponse.DeviceListResultData.DeviceListBean.DeviceListBeanChannel> channels = devElement.getChannels();
        if (devElement != null && devElement.getChannels() != null) {
            for (DeviceListResponse.DeviceListResultData.DeviceListBean.DeviceListBeanChannel chnlElement : channels) {
                ChannelInfo chnlInfo = new ChannelInfo();
                chnlInfo.setDeviceCode(devElement.getDeviceId());
                chnlInfo.setDeviceModel(devElement.getDeviceModel());
                chnlInfo.setEncryptMode(devElement.getEncryptMode());
                chnlInfo.setIndex(chnlElement.getChannelId());
                chnlInfo.setName(chnlElement.getChannelName());
                chnlInfo.setBackgroudImgURL(chnlElement.getChannelPicUrl());
                chnlInfo.setCloudMealStates(chnlElement.getCsStatus());
                chnlInfo.setAlarmStatus(chnlElement.getAlarmStatus());
                // // 是否为云台设备
                // if ((chnlElement.channelAbility != null
                // && chnlElement.channelAbility.contains("PTZ"))
                // || devElement.ability.contains("PTZ")) {
                // chnlInfo.setType(ChannelType.PtzCamera);
                // }
                // else {
                // chnlInfo.setType(ChannelType.Camera);
                // }
                // 是否支持加密
                if (devElement.getAbility().contains("HSEncrypt")) {
                    chnlInfo.setEncrypt(1);
                } else {
                    chnlInfo.setEncrypt(0);
                }
                // 设置设备能力集
                chnlInfo.setAbility(StringToAbility(devElement.getAbility()) | StringToAbility(chnlElement.getChannelAbility()));

                // 设备与通道同时在线，通道才算在线
                if (chnlElement.isChannelOnline())
                    switch (devElement.getStatus()) {
                        case "0":
                            chnlInfo.setState(ChannelInfo.ChannelState.Offline);
                            break;
                        case "1":
                            chnlInfo.setState(ChannelInfo.ChannelState.Online);
                            break;
                        case "3":
                            chnlInfo.setState(ChannelInfo.ChannelState.Upgrade);
                            break;
                    }
                channelList.add(chnlInfo);
            }
        }



        // 解析分享设备通道列表
//        if (shareDevElement != null && shareDevElement.channels != null) {
//            for (ShareDeviceList.ResponseData.DevicesElement.ChannelsElement chnlElement : shareDevElement.channels) {
//                ChannelInfo chnlInfo = new ChannelInfo();
//                chnlInfo.setDeviceCode(shareDevElement.deviceId);
//                chnlInfo.setDeviceModel(shareDevElement.deviceModel);
//                chnlInfo.setEncryptMode(shareDevElement.encryptMode);
//                chnlInfo.setIndex(chnlElement.channelId);
//                chnlInfo.setName(chnlElement.channelName + "[shared]");
//                chnlInfo.setBackgroudImgURL(chnlElement.channelPicUrl);
//                chnlInfo.setAlarmStatus(chnlElement.alarmStatus);
//                // // 是否为云台设备
//                // if ((chnlElement.channelAbility != null
//                // && chnlElement.channelAbility.contains("PTZ"))
//                // || devElement.ability.contains("PTZ")) {
//                // chnlInfo.setType(ChannelType.PtzCamera);
//                // }
//                // else {
//                // chnlInfo.setType(ChannelType.Camera);
//                // }
//                // 是否支持加密
//                if (shareDevElement.ability.contains("HSEncrypt")) {
//                    chnlInfo.setEncrypt(1);
//                } else {
//                    chnlInfo.setEncrypt(0);
//                }
//                // 设置设备能力集
//                chnlInfo.setAbility(StringToAbility(shareDevElement.ability));
//
//                // 设备与通道同时在线，通道才算在线
//                if (chnlElement.channelOnline)
//                    switch (shareDevElement.status) {
//                        case 0:
//                            chnlInfo.setState(ChannelInfo.ChannelState.Offline);
//                            break;
//                        case 1:
//                            chnlInfo.setState(ChannelInfo.ChannelState.Online);
//                            break;
//                        case 3:
//                            chnlInfo.setState(ChannelInfo.ChannelState.Upgrade);
//                            break;
//                    }
//                channelList.add(chnlInfo);
//            }
//        }
        return channelList;
    }

    public static int StringToAbility(String strAbility) {
        int ability = 0;
        if (strAbility == null) {
            return ability;
        }

        if (strAbility.contains("WLAN")) {
            ability |= ChannelInfo.Ability.WLAN;
        }
        if (strAbility.contains("AlarmPIR")) {
            ability |= ChannelInfo.Ability.AlarmPIR;
        }
        if (strAbility.contains("AudioTalk")) {
            ability |= ChannelInfo.Ability.AudioTalk;
        }
        if (strAbility.contains("VVP2P")) {
            ability |= ChannelInfo.Ability.VVP2P;
        }
        if (strAbility.contains("PTZ")) {
            ability |= ChannelInfo.Ability.PTZ;
        }
        if (strAbility.contains("HSEncrypt")) {
            ability |= ChannelInfo.Ability.HSEncrypt;
        }
        if (strAbility.contains("CloudStorage")) {
            ability |= ChannelInfo.Ability.CloudStorage;
        }
        return ability;
    }

    public static final String LeChengUid = "LeChengUserId";

    public static String getLeChengStorgeKey(String key) {
        String mobile = PersistentMgr.readKV(LeChengUid, null);
        if (TextUtils.isEmpty(mobile)) {
            return null;
        }

        return "LeCeng_"+mobile+"_"+key;
    }

    public static void setLeCengStorgeKV(String key, String value) {
        key = getLeChengStorgeKey(key);
        if (TextUtils.isEmpty(key)) {
            return;
        }

        PersistentMgr.putKV(key, value);
    }

    public static boolean isResponseOK(LeChengResponse response) {
        String code = response.getResult().getCode();
        String msg = response.getResult().getMsg();
        if (!TextUtils.isEmpty(code) && code.trim().equals("0")) {
            return true;
        } else {
            if (TextUtils.isEmpty(msg)) {
                msg = ToolsApplication.getContext().getResources().getString(R.string.failed);
            }
            Toast.makeText(ToolsApplication.getContext(), msg, Toast.LENGTH_SHORT).show();
            return false;
        }
    }

}
