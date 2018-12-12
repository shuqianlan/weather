package com.ilifesmart.javaprogect.ExectorTestDrive;

import java.util.concurrent.Executor;

public class WithInThreadExecutor implements Executor {

	private Runnable task;

	public WithInThreadExecutor(Runnable task) {
		this.task = task;
	}

	@Override
	public void execute(Runnable runnable) {
		runnable.run();
	}

	public void execute() {
		if (task != null) {
			execute(task);
		} else {
			System.out.println("none things.");
		}
	}
}
