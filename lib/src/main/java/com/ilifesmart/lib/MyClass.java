package com.ilifesmart.lib;

import java.util.ArrayList;
import java.util.List;

public class MyClass {
	public static void main(String args[]) {
		System.out.println("Hello World!");

//		ArrayList<Integer> src = new ArrayList<>();
//		ArrayList<Integer> dst = new ArrayList<>();
//
//		for (int i = 0; i < 10; i++) {
//			src.add(i);
//		}
//
//		System.out.println("src.size " + src.size());
//		src.remove(0);
//		System.out.println("src.size " + src.size());

		List<String> texts = new ArrayList<>();

		for (int i = 0; i < 10; i++) {
			texts.add("Item-"+(i+1));
		}

		System.out.println("size 1 " + texts.size());
		texts.remove(1);
		System.out.println("size 2 " + texts.size());
	}
}
