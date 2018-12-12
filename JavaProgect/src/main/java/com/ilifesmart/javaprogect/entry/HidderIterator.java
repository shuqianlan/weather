package com.ilifesmart.javaprogect.entry;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static java.lang.Thread.sleep;

public class HidderIterator {
	private final Set<Integer> set = new HashSet<>();

	public synchronized void add(Integer i) {
		set.add(i);
	}

	public synchronized void remove(Integer i) {
		set.remove(i);
	}

	public void addTenThings() {
		Random r = new Random();
		for (int i = 0; i < 10; i++) {
			try {
				sleep(1000);
			} catch(Exception ex) {
				ex.printStackTrace();
			}
			add(r.nextInt());
			System.out.println("DEBUG: added ten elements to " + set);
		}
	}
}
