
> Matrix详解
- http://www.gcssloop.com/customview/Matrix_Basic#fangfa
- http://www.gcssloop.com/customview/Matrix_Method

> Camera

- http://www.gcssloop.com/customview/matrix-3d-camera
- rotateX(x); x>0则为y正轴部分向里，y负轴部分向外. 不考虑z，角度和y有关，y越大，则角度也就越大.
- rotateY(y); y>0则为x正轴部分向里，x负轴部分向外. 不考虑z，角度和x有关，x越大，则角度也就越大.
- rotateX(z); z>0则逆时针旋转.

> 旋转中心

- 旋转中心是坐标原点，对于图片就是左上角.
- 基于中心点旋转.
 ```
 Matrix temp = new Matrix();
 camera.getMatrix(temp);
 temp.preTranslate(-centerX, -centerY); // 将旋转中心移到和Camera位置相同
 temp.postTranslate(centerX, centerY); // 将旋转中心移到原先的位置
 ```

> 贝塞尔曲线

- https://bezier.method.ac/ 贝塞尔曲线游戏.