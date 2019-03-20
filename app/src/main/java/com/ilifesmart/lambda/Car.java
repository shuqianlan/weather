package com.ilifesmart.lambda;

import android.util.Log;

import java.util.Arrays;
import java.util.List;

public class Car {

	public static final String TAG = "Lambda";

/*
* Lambda表达式如下:
*/

//	(parameters) -> expression
//	或
//	(parameters) ->{ statements; }
//  1. 不需要参数,返回值为 5
//  () -> 5
//
// 2. 接收一个参数(数字类型),返回其2倍的值
//	x -> 2 * x
//
// 3. 接受2个参数(数字),并返回他们的差值
//	(x, y) -> x – y
//
// 4. 接收2个int型整数,返回他们的和
//	(int x, int y) -> x + y
//
// 5. 接受一个 string 对象,并在控制台打印,不返回任何值(看起来像是返回void)
//	(String s) -> System.out.print(s)

/*
* 方法引用
* */



	@FunctionalInterface
	public interface Supplier<T> {
		T get();
	}

	public static Car create(final Supplier<Car> supplier) {
		return supplier.get();
	}

	public static void collide(final Car car) {
		System.out.println("Car " + car);
	}

	public void follow(final Car another) {
		Log.d(TAG, "follow: another " + another);
	}

	public void repair() {
		Log.d(TAG, "repair: " + this);
	}

	@Override
	public String toString() {
		return "{ Lambda }";
	}

	static {
		final Car car = Car.create(Car::new);
		final List<Car> cars = Arrays.asList(car);
		Log.d(TAG, "static initializer: car " + car);

	}

	public interface testFilter {
		// 默认方法
		default void print() {
			Log.d(TAG, "print: default ..");
		}
		
		// 静态方法
		static void staticMethod() {
			Log.d(TAG, "staticMethod: static method");
		}

	}
}
