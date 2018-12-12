package com.ilifesmart.javaprogect.ExectorTestDrive;

import java.util.concurrent.Executor;

public class ThreadPerTaskExecutor implements Executor {
	@Override
	public void execute(Runnable runnable) {
		new Thread(runnable).start();
	}
}
