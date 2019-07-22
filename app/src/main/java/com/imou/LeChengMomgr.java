package com.imou;

import java.util.ArrayList;
import java.util.List;

public class LeChengMomgr {
    private List<ChannelInfo> devicesChannels = new ArrayList<>();
    private List<ChannelInfo> sharedChannels = new ArrayList<>();
    private List<ChannelInfo> allChannels = new ArrayList<>();
    private String token;

    private static Object lock = new Object();
    private static volatile LeChengMomgr Instance = null;

    public static LeChengMomgr getInstance() {
        if (Instance == null) {
            synchronized (lock) {
                if (Instance == null) {
                    Instance = new LeChengMomgr();
                }
            }
        }
        return Instance;
    }

    private LeChengMomgr() {}

    public void addChannels(List<ChannelInfo> infos) {
        allChannels.addAll(infos);
    }

    public ChannelInfo getChannelInfo(String uuid) {
        ChannelInfo result = null;
        for(ChannelInfo info:allChannels) {
            if (info.getUuid().equals(uuid)) {
                result = info;
            }
        }

        return result;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
