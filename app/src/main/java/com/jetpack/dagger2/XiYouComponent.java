package com.jetpack.dagger2;

import android.app.Activity;
import dagger.Component;

import javax.inject.Singleton;

/* 绑定数据源和使用的地方 */
@Component(modules = {XiYouModule.class})
@Singleton // 表明呗依赖的对象在应用的生命周期只有一个实例
public interface XiYouComponent {
	void inject(WuKong wk);
	void inject(Activity activity);
}
