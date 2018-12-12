package com.ilifesmart.javaprogect.entry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PersonSet {
	// 封闭实例.
	private final Set<MOBaseNoSafe> mySet = new HashSet<>();
	private List<MOBaseNoSafe> mMos = Collections.synchronizedList(new ArrayList<MOBaseNoSafe>()); // 装饰器模式.

	public synchronized void addPerson(MOBaseNoSafe mo) {
		mySet.add(mo);
	}

	public synchronized boolean containsMO(MOBaseNoSafe mo) {
		return mySet.contains(mo);
	}

}
