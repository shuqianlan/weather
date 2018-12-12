package com.ilifesmart.javaprogect.entry;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import static java.lang.Thread.sleep;

public class FutureTaskTestDrive {
	private final FutureTask<MOBaseThreadSafe> future = new FutureTask<MOBaseThreadSafe>(new Callable<MOBaseThreadSafe>() {
		@Override
		public MOBaseThreadSafe call() throws Exception {
			return getTest();
		}
	});

	private final Thread mThread = new Thread(future);

	public void start() {
		mThread.start();
	}

	private MOBaseThreadSafe getTest() {
		try {
			sleep(3000);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return new MOBaseThreadSafe().setCgy("ep").setCls("SL_SC_BB_V1").setmId("**/m/2841").setName("随心开关").setNid("2841").setAgtId("****");
	}

	public MOBaseThreadSafe get() throws Exception {
		start();
		return future.get();
	}
}
