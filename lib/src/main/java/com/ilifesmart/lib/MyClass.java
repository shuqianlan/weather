package com.ilifesmart.lib;

import java.util.ArrayList;
import java.util.Arrays;

public class MyClass {
	public static void main(String args[]) {
		System.out.println("Hello World!");

		ArrayList<Integer> src = new ArrayList<>();
		ArrayList<Integer> dst = new ArrayList<>();

		for (int i = 0; i < 10; i++) {
			src.add(i);
		}

		dst = src;

		System.out.println("ArrayList.show(dst) " + Arrays.toString(dst.toArray()));
	}
}
