package com.ilifesmart.javaprogect.entry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Momgr {

	private final Map<String, MOBaseNoSafe> mAgtCache = new ConcurrentHashMap<>();
	private final Map<String, MOBaseNoSafe> mIOCache = new ConcurrentHashMap<>();
	private final Map<String, MOBaseNoSafe> mAICache = new ConcurrentHashMap<>();
	private final Map<String, MOBaseNoSafe> mFavCache = new ConcurrentHashMap<>();

	private final Map<String, MOBaseNoSafe> unModifiableAgtMap = new ConcurrentHashMap<>();
	private final Map<String, MOBaseNoSafe> unModifiableIOMap = new ConcurrentHashMap<>();
	private final Map<String, MOBaseNoSafe> unModifiableAIMap = new ConcurrentHashMap<>();
	private final Map<String, MOBaseNoSafe> unModifiableFavMap = new ConcurrentHashMap<>();

	private Momgr() {

	}
}
