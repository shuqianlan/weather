package com.ilifesmart.javaprogect.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Memoizer2<A,V> implements Computable<A,V> {

	private final Map<A,V> cache = new ConcurrentHashMap<>();
	private final Computable<A,V> c;

	@Override
	public V compute(A arg) throws InterruptedException {
		V result = cache.get(arg);
		if (result == null) {
			result = c.compute(arg);//若某个线程启动了一个开销很大的计算，而其他线程不知道，可能会造成重复计算.
			cache.put(arg, result);
		}

		return result;
	}

	public Memoizer2(Computable c) {
		this.c = c;
	}
}
