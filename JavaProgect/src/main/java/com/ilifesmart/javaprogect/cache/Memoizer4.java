package com.ilifesmart.javaprogect.cache;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class Memoizer4<A,V> implements Computable<A,V> {

	private final Map<A,Future<V>> cache = new ConcurrentHashMap<>();
	private final Computable<A,V> c;

	public Memoizer4(Computable c) {
		this.c = c;
	}

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
			cache.putIfAbsent(arg, result); // 避免多线程下某计算等待过长的问题
			ft.run();
		}

		try {
			V ret = result.get(); //阻塞等待结果.
			return ret;
		} catch(Exception ex) {
			ex.printStackTrace();
			throw new InterruptedException(ex.getMessage());
		}
	}
}
