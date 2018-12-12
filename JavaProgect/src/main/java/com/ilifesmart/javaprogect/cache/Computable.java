package com.ilifesmart.javaprogect.cache;

public interface Computable<A,V> {
	V compute(A arg) throws InterruptedException;
}
