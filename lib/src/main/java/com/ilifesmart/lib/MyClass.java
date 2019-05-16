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
		String str1 = "110000,,,11,北京市,,,,";
		String str2 = "110000,110100,,11,北京市,1,市辖区,,";
		String str3 = "110000,110200,,11,北京市,2,县,,";
		String str4 = "110000,110100,110117,11,北京市,1,市辖区,17,平谷区";

		String[] arrays1 = str1.split(",");
		System.out.println("splits1 " + Arrays.toString(arrays1));
		String[] arrays2 = str2.split(",");
		System.out.println("splits2 " + Arrays.toString(arrays2));
		String[] arrays3 = str3.split(",");
		System.out.println("splits3 " + Arrays.toString(arrays3));
		String[] arrays4 = str4.split(",");
		System.out.println("splits4 " + Arrays.toString(arrays4));

		for(String value:arrays4) {
			System.out.println("value ============== 4 " + value);
		}

		for(String value:arrays3) {
			System.out.println("value ============== 3 " + value);
		}

		for(String value:arrays2) {
			System.out.println("value ============== 2 " + value);
		}

		for(String value:arrays1) {
			System.out.println("value ============== 1 " + value);
		}

		String str5 = "110000";
		System.out.println("sub(0,2) " + str5.substring(0, 2));
		System.out.println("sub(2,4) " + str5.substring(2, 4));
		System.out.println("sub(4,6) " + str5.substring(4, 6));

		System.out.println("sub(0,1) " + str5.substring(0, 1));
	}
}
