package com.jetpack.dagger2;

import dagger.Module;
import dagger.Provides;

/*
* @Module和@Provides提供了被依赖的对象
* @Inject在@Component接口出现的地方则指定需要注入的地方.
* @Module:提供对象实例化的功能!
*
* 数据源实例化
* */

@Module
public class XiYouModule {

	@Provides // 创建被依赖的对象
	WuKong provideWuKong() {
		return new WuKong();
	}

	@Provides
	GinGuBang provideGinGuBang() {
		return new GinGuBang();
	}
}
