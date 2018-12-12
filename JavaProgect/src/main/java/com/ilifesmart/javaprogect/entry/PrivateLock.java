package com.ilifesmart.javaprogect.entry;

public class PrivateLock {
	private Object myLock = new Object();

	public void doSomeThing() {
		synchronized (myLock) {
			// TODO:
		}
	}
}
