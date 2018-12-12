package com.ilifesmart.javaprogect.nature_module_models;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.lang.Thread.sleep;

public class Momgr {
	private final Map<String, Ep> mEpCache = new ConcurrentHashMap(new HashMap<>());
	private final Map<String, Io> mIOCache = new ConcurrentHashMap(new HashMap<>());
	private static Momgr instance;

	private Momgr() {}

	public static Momgr getInstance() {
		if (instance == null) {
			synchronized (Momgr.class) {
				if (instance == null) {
					instance = new Momgr();
				}
			}
		}

		return instance;
	}

	private Ep manualDelay(Ep ep) {
		try {
			sleep(2000);
		} catch(Exception ex) {
			ex.printStackTrace();
		}

		return ep;
	}

	public void putEp(Ep ep) {
		mEpCache.put(ep.getId(), manualDelay(ep));
	}

	public Ep getEp(String epId) {
		return mEpCache.getOrDefault(epId, null);
	}

	public Ep removeEp(String epId) {
		Ep ret = getEp(epId);
		mEpCache.remove(ret);
		return ret;
	}

	public void setIO(Io io) {
		mIOCache.put(io.getId(), io);
	}

	public Io getIO(String ioId) {
		return mIOCache.getOrDefault(ioId, null);
	}

	public Io removeIo(String ioId) {
		Io ret = getIO(ioId);
		mIOCache.remove(ret);
		return ret;
	}

}
