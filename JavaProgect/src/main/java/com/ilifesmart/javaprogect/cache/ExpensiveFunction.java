package com.ilifesmart.javaprogect.cache;

import java.math.BigInteger;

public class ExpensiveFunction implements Computable<String, BigInteger> {
	@Override
	public BigInteger compute(String arg) throws InterruptedException {
		return new BigInteger(arg);
	}


}
