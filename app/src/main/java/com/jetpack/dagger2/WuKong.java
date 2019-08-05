package com.jetpack.dagger2;

import javax.inject.Inject;

public class WuKong {

	@Inject
	GinGuBang ginGuBang;

	@Inject
	public WuKong() {

	}

	public String useGinGuBang() {
		return this.ginGuBang.use();
	}
}
