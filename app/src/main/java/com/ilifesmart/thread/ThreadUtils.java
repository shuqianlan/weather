package com.ilifesmart.thread;

import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadUtils {
	public static final String TAG = "ThreadUtils";
	/*
	 * newCachedThreadPool: 创建一个新的缓冲池，若无或不可用，则新建一个线程。会自动收缩
	 * newFixedThreadPool(5):创建固定大小的线程池
	 * newSingleThreadExecutor: 创建单线程池，无边界队列
	 * newScheduledThreadPool: 延期或定时任务执行的线程池
	 *
	 * */

	private static final ThreadFactory sThreadFactory = new ThreadFactory() {
		private AtomicInteger mCount = new AtomicInteger(1);
		@Override
		public Thread newThread(Runnable r) {
			return new Thread(r, "Nature #"+mCount.getAndIncrement());
		}
	};

	private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
	// We want at least 2 threads and at most 4 threads in the core pool,
	// preferring to have 1 less than the CPU count to avoid saturating
	// the CPU with background work
	private static final int CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 4));

	private static final RejectedExecutionHandler NatureHandler = new NewThreadPolicy();
	public static final Executor THREAD_POOL_EXECUTOR;
	static {
		ThreadPoolExecutor executor = new ThreadPoolExecutor(
						CORE_POOL_SIZE,
						6,
						60L,
						TimeUnit.SECONDS,
						new SynchronousQueue<Runnable>(),
						sThreadFactory,
						NatureHandler
		);
		executor.allowCoreThreadTimeOut(true);
		THREAD_POOL_EXECUTOR = executor;
	}

	private static class NewThreadPolicy implements RejectedExecutionHandler {
		public NewThreadPolicy() {}

		@Override
		public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
			if (!executor.isShutdown()) {
				new Thread(r);
			}
		}
	}
}
