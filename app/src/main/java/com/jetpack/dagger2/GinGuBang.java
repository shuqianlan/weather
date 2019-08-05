package com.jetpack.dagger2;

import javax.inject.Inject;

public class GinGuBang {

	@Inject // 数据源使用的地方
	public GinGuBang() { }

	public String use() {
		return "user Jing gu bang";
	}
}
