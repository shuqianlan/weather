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

> 避免多个Activity, 适用于同一Style的View.