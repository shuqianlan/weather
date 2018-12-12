package com.ilifesmart.javaprogect.entry;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTestDrive {
	// 闭锁实例.

	public static long timeTasks(int nThreads, final Runnable task) {
		final CountDownLatch startGate = new CountDownLatch(1);
		final CountDownLatch endGate = new CountDownLatch(nThreads);

		for (int i = 0; i < nThreads; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						System.out.println("-------------------- ");
						startGate.await();
						task.run();
					} catch(Exception ex) {
						ex.printStackTrace();
					} finally {
						endGate.countDown();
						System.out.println("--------------- endGate-Count " + endGate.getCount());
					}
				}
			}).start();
		}

		long start = System.nanoTime();
		System.out.println("00000000 - 0");
		startGate.countDown();
		System.out.println("00000000 - 1");
		try {
			System.out.println("@@@@@@@@@@@@@@");
			endGate.await();
			System.out.println("------------------- end");
		} catch(Exception ex) {
			ex.printStackTrace();
		}

		long end = System.nanoTime();
		return (end-start);
	}
}
