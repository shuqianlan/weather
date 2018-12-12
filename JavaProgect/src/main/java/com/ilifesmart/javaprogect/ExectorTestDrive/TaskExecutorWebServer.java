package com.ilifesmart.javaprogect.ExectorTestDrive;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskExecutorWebServer {
	public static final int NTHREADS = 100;
	private static final Executor exec = Executors.newFixedThreadPool(NTHREADS);

	public void doAccept() {
		while (true) {
			Runnable task = new Runnable() {
				@Override
				public void run() {
					// TODO:handleRequest
				}
			};

			exec.execute(task);
		}
	}
}
