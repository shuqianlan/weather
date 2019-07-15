package com.ilifesmart.lib;

import com.ilifesmart.annotation.Check;
import com.ilifesmart.annotation.Perform;
import com.ilifesmart.annotation.TestAnnotation;
import com.ilifesmart.reflect.ReflectDemo;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

@TestAnnotation(id=0, msg="MyClass.TestDemo")

public class MyClass {

	@Check(value = "Hi")
	int a;

	@Perform
	public void testMethod() {}

	@SuppressWarnings("deprecation")
	public void test1() {
		Hero hero = new Hero();
		hero.say();
		hero.spreak();
	}

	public static void main(String args[]) {
		System.out.println("Hello World!");

		int val = 0x80;
		System.out.println("val:" + (val >>>7));

		boolean hasAnnotation = MyClass.class.isAnnotationPresent(TestAnnotation.class);
		System.out.println("hasAnnotation:" + hasAnnotation);
		if (hasAnnotation) {
			TestAnnotation annotation = MyClass.class.getAnnotation(TestAnnotation.class);
			System.out.println("id: " + annotation.id());
			System.out.println("msg: " + annotation.msg());
		}

		try {
			Field a = MyClass.class.getDeclaredField("a");
			a.setAccessible(true);
			Check check = a.getAnnotation(Check.class);
			if (check != null) {
				System.out.println("Check: value: " + check.value());
			}

			Method method = MyClass.class.getDeclaredMethod("testMethod");
			if (method != null) {
				Annotation[] ans = method.getAnnotations();
				for(Annotation annotation: ans) {
					System.out.println("annotation: " + annotation.annotationType().getSimpleName());
				}
			}
		} catch (NoSuchFieldException ex) {
			ex.printStackTrace();
		} catch (NoSuchMethodException ex) {
			ex.printStackTrace();
		}

//		TestTool.testNoBus(); // 注解测试!
		ReflectDemo.test();

		List<String> strs = new ArrayList<>();
		strs.add("Hello");
		strs.add("Alo");
		Collections.sort(strs);
		System.out.println("strs: " + Arrays.toString(strs.toArray()));
	}

	/*
	*
	* 信号量:线程同步.
	* tryAcquire()若可用，则调用acquire，不可用则return false
	* acquire：消费1个
	* release: 回收1个
	* */
	private static void semaphore() {
		ExecutorService executorService = Executors.newCachedThreadPool();
		final Semaphore semaphore = new Semaphore(5, false);
		AtomicInteger deprecated = new AtomicInteger(0);

		for (int i = 0; i < 40; i++) {
			final int NO = i;
			Runnable runable = new Runnable() {
				@Override
				public void run() {
					try {
						if (semaphore.tryAcquire()) {
							System.out.println("consumeing " + NO);
							Thread.sleep((long) (Math.random() * 10_000));
							semaphore.release();
						}
					} catch(Exception ex) {
						ex.printStackTrace();
					}
				}
			};

			executorService.execute(runable);
		}
	}
}
