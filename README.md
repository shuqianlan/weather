# weather
Weather

## MiClockView(仿小米时钟)

参照:https://blog.csdn.net/qq_31715429/article/details/54668668
> 碰到的问题

- 卡顿严重
  * 解决方案
    - 保存不变元素的实例，避免多次重复创建。修改后，Profiler中Native内存稳定
    - 避免在onDraw中创建对象，避免对象释放JVM引发UI线程阻塞导致卡顿问题.

- VSYNC信号
  UI刷新需在16ms中完成，否则就会出现卡顿问题.

## EmbeddedCompassView(万向轮)

## EmbeddedViewStackContainer(FrameLayout)

## 区域选择器
	
> 避免多个Activity, 适用于同一Style的View.

## 乐橙摄像头

> 使用RxJava+Retrofit+Gson的方式，目前功能包含认证，播放，云台控制，视频录制，对讲等。部分功能已实现但未设置调用入口. 代码是含Kotlin及Java
