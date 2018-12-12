package com.ilifesmart.javaprogect.entry;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class MonitorVehicleTracker {
	private final Map<String, MOBaseNoSafe> mMoMaps;
	private final List<String> lists = new CopyOnWriteArrayList<>();
	private final ConcurrentHashMap<String, MOBaseNoSafe> mMos = new ConcurrentHashMap<>();

	public MonitorVehicleTracker() {
		mMoMaps = new HashMap<>();
	}

	// 大数据的情况下会出现问题.
	private static Map<String,MOBaseNoSafe> deepCopy(Map<String,MOBaseNoSafe> m) {
		Map<String,MOBaseNoSafe> resule = new HashMap<>();

		for(String id : m.keySet()) {
			resule.put(id, new MOBaseNoSafe().setmId(id));
		}


		return Collections.unmodifiableMap(resule); // 返回一个只读的map.
	}
}
