package com.ilifesmart.javaprogect.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class Memoizer3<A,V> implements Computable<A,V> {

	private final Map<A,Future<V>> cache = new ConcurrentHashMap<>();
	private final Computable<A,V> c;

	@Override
	public V compute(A arg) throws InterruptedException {
		Future<V> result = cache.get(arg);
		if (result == null) {
			Callable<V> eval = new Callable<V>() {
				@Override
				public V call() throws Exception {
					return c.compute(arg);
				}
			};
			FutureTask<V> ft = new FutureTask<>(eval);
			result = ft;
			cache.put(arg, result);
			ft.run();
		}

		try {
			return result.get();
		} catch(Exception ex) {
			ex.printStackTrace();
			throw new InterruptedException("xxxx");
		}
	}

	public Memoizer3(Computable c) {
		this.c = c;
	}
}
