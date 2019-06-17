package com.ilifesmart.lib;

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

//		List<String> texts = new ArrayList<>();
//
//		for (int i = 0; i < 10; i++) {
//			texts.add("Item-"+(i+1));
//		}
//
//		System.out.println("size 1 " + texts.size());
//		texts.remove(1);
//		System.out.println("size 2 " + texts.size());

		int val = 4;
		System.out.println("val * 4 " + val * 4);
		System.out.println("val << 2 " + (val << 2));

		int dura = 0xffff;
		dura >>>= 7;
		System.out.println("rshift(7) " + Integer.toHexString(dura));
		int duration = dura & 0xff;
		System.out.println("duation " + Integer.toHexString(duration));
		int en = dura >>> 8;
		System.out.println("en " + en);

		int nval = 1;
		nval = (nval << 8) | 0xff;
		System.out.println("Intet " + Integer.toHexString(nval));
		nval = nval << 7;
		System.out.println("nvalue " + Integer.toHexString(nval));

	}
}
