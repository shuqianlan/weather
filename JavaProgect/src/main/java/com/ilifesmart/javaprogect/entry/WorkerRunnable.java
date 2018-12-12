package com.ilifesmart.javaprogect.entry;

import com.ilifesmart.javaprogect.cache.Memoizer4;

import static java.lang.Thread.sleep;

public class WorkerRunnable implements Runnable {

	private final String arg;
	private final String TAG;
	private final Memoizer4<String,Integer> map;

	public WorkerRunnable(String arg, Memoizer4<String,Integer> map, String TAG) {
		this.arg = arg;
		this.map = map;
		this.TAG = TAG;
	}

	@Override
	public void run() {
		try {
			sleep(2000);
		} catch(Exception ex) {
			ex.printStackTrace();
		}

		try {
			System.out.println(TAG + "[ map.get("+arg+") = " + map.compute(arg) + " ]");
		} catch(Exception ex) {
			ex.printStackTrace();
		}

	}
}
