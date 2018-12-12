package com.ilifesmart.javaprogect.entry;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

public class BoundedHashSet<T> {
	private final Set<T> set;
	private final Semaphore sem;

	public BoundedHashSet(int bound) {
		set = Collections.synchronizedSet(new HashSet<>());
		sem = new Semaphore(bound);
	}

	public boolean add(T o) throws InterruptedException {
		sem.acquire();
		boolean wasAdded = false;
		try {
			wasAdded = set.add(o); // return false:原先已经拥有，并不做任何改变.
			return wasAdded;
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			if (!wasAdded) {
				sem.release();
			}
		}

		return wasAdded;
	}

	public boolean remove(Object o) {
		boolean wasRemoved = set.remove(o); // return true:原先包含
		if (wasRemoved) {
			sem.release();
		}

		return wasRemoved;
	}
}
