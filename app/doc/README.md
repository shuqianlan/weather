# weather
-- 

# SurfaceView

```
surfaceCreated:   显示时调用
surfaceDestroyed: 隐藏时调用
surfaceChanged：  surface尺寸发生变化
需要获得SurfaceHolder，并添加Holder.CallBack


继承于View，内嵌一块用于绘制的surface，可以控制其格式，位置及尺寸.
surface为纵深排序(Z-ordered)
surfaceview采用双缓冲技术，可在子线程刷新UI

:: 部分说明 https://www.xuebuyuan.com/3236956.html
```