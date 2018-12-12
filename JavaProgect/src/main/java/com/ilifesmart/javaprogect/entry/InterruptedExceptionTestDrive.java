package com.ilifesmart.javaprogect.entry;

public class InterruptedExceptionTestDrive implements Runnable{

	@Override
	public void run() {
		try {
			throw new InterruptedException();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}
