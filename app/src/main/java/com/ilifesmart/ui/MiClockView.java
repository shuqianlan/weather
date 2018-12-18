package com.ilifesmart.ui;

import android.animation.PropertyValuesHolder;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.BounceInterpolator;

import com.ilifesmart.utils.DensityUtils;
import com.ilifesmart.weather.R;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

public class MiClockView extends View {
	private int mMaxHourTextWidth;
	private int mSuitableTextAngle;
	private int mCenterX;
	private int mCenterY;
	private int mOutSideRadius;
	private int mHourRadius;
	private int mMinuteRadius;
	private int mSecondRadius;

	private Paint mBackgroundPaint;
	private Paint mOutRadiusPaint;
	private Paint mSecondPaint;
	private Paint mSweepPaint;
	private Paint mTextPaint;
	private Paint mHourPaint;
	private Paint mMinutePaint;

	private float mCurrHour;
	private float mCurrMinute;
	private float mCurrSeconds;
	private float mCurrMills;

	private float mHourDegree;
	private float mMinuteDegree;
	private float mSecondDegree;
	private float mSweepDegree;

	private int mDarkColor = Color.DKGRAY;
	private int mLightColor = Color.LTGRAY;
	private int mOutSideStrokeWidth = 2; // dp
	private int mOutSideStrokeColor = mLightColor;
	private int mHourTextSize = 24;      // sp
	private int mHourTextColor = mDarkColor;
	private int mSecondTriangleColor = Color.WHITE;
	private int mHourMaskColor = mLightColor;
	private int mMinuteMaskColor = Color.WHITE;
	private int mSweepStartColor = Color.TRANSPARENT;
	private int mSweepEndColor = Color.WHITE;
	private int mBackgroundColor = Color.parseColor("#FF4D7EAB");
	private int mCenterCircleColor = Color.parseColor("#FF4D7EAB");
	private SweepGradient mSweepGradient;
	private final int mSweepAlpha = 25;

	private Path mSecondTrianglePath = new Path();
	private Path mHourMarkPath = new Path();
	private Path mMinuteMarkPath = new Path();
	private Rect mTextRect = new Rect();
	private Matrix mSweepGradientMatrix;
	private Matrix mCameraMatrix;
	private final float MAX_CAMER_ROTATE = 10;
	private float mMaxCanvasTranslate;

	private Camera mCamera;
	private float mCameraRotateX;
	private float mCameraRotateY;
	private float mCanvasTranslateX;
	private float mCanvasTranslateY;
	private ValueAnimator mShakeAnim;

	public MiClockView(Context context) {
		this(context, null);
	}

	public MiClockView(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MiClockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		initizlize(attrs);
	}

	private void initizlize(AttributeSet attrs) {
		if (attrs != null) {
			TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.MiClockView);
			mOutSideStrokeWidth = DensityUtils.dp2px(getContext(), a.getDimension(R.styleable.MiClockView_outSideRadiusStrokeWidth, 2));
			mOutSideStrokeColor = a.getColor(R.styleable.MiClockView_outSideRadiusStrokeColor, mOutSideStrokeColor);
			mHourTextSize = DensityUtils.sp2px(getContext(), a.getDimension(R.styleable.MiClockView_hourTextSize, mHourTextSize));
			mHourTextColor = a.getColor(R.styleable.MiClockView_hourTextColor, mHourTextColor);
			mSecondTriangleColor = a.getColor(R.styleable.MiClockView_secondTriangleColor, mSecondTriangleColor);
			mHourMaskColor = a.getColor(R.styleable.MiClockView_hourMarkColor, mHourMaskColor);
			mMinuteMaskColor = a.getColor(R.styleable.MiClockView_minuteMarkColor, mMinuteMaskColor);
			mSweepStartColor = a.getColor(R.styleable.MiClockView_sweepStartColor, mSweepStartColor);
			mSweepEndColor = a.getColor(R.styleable.MiClockView_sweepEndColor, mSweepEndColor);
			mBackgroundColor = a.getColor(R.styleable.MiClockView_backgroundColor, mBackgroundColor);
			mCenterCircleColor = a.getColor(R.styleable.MiClockView_centerCircleColor, mCenterCircleColor);

			a.recycle();
		}

		mOutRadiusPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mOutRadiusPaint.setStyle(Paint.Style.STROKE);
		mOutRadiusPaint.setColor(mOutSideStrokeColor);
		mOutRadiusPaint.setStrokeWidth(mOutSideStrokeWidth);

		mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mTextPaint.setStyle(Paint.Style.FILL);
		mTextPaint.setTextSize(mHourTextSize);
		mTextPaint.setColor(mHourTextColor);
		mTextPaint.setTextAlign(Paint.Align.CENTER);

		mSweepPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mSweepPaint.setStyle(Paint.Style.STROKE);
		mSweepPaint.setAlpha(mSweepAlpha);

		mSecondPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mSecondPaint.setStyle(Paint.Style.FILL);
		mSecondPaint.setColor(mSecondTriangleColor);

		mHourPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mHourPaint.setStyle(Paint.Style.FILL);
		mHourPaint.setColor(mHourMaskColor);

		mMinutePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mMinutePaint.setStyle(Paint.Style.FILL);
		mMinutePaint.setColor(mMinuteMaskColor);

		mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mBackgroundPaint.setStyle(Paint.Style.FILL);
		mBackgroundPaint.setColor(mBackgroundColor);

		mSweepGradientMatrix = new Matrix();
		mCameraMatrix = new Matrix();
		mCamera = new Camera();
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		int contW = w - getPaddingLeft() - getPaddingRight();
		int contH = h - getPaddingTop() - getPaddingBottom();
		mCenterX = getPaddingLeft() + contW/2;
		mCenterY = getPaddingTop() + contH/2;

		Rect rect = new Rect();
		mTextPaint.getTextBounds("12", 0, "12".length(), rect);
		mMaxHourTextWidth = rect.width() + 20;
		mOutSideRadius = Math.min(contH, contW)/2-2-rect.height()/2;
		mHourRadius = (int) (mOutSideRadius * 0.4);
		mMinuteRadius = (int) (mOutSideRadius * 0.6);
		mSuitableTextAngle = (int) Math.ceil(Math.toDegrees(Math.acos((mOutSideRadius*mOutSideRadius*2.0-mMaxHourTextWidth*mMaxHourTextWidth)/(2*mOutSideRadius*mOutSideRadius))));
		mSecondRadius = (int) (mOutSideRadius * 0.7);
		mSweepGradient = new SweepGradient(mCenterX, mCenterY, new int[] {mSweepStartColor, mSweepEndColor}, new float[]{0.75f, 1});
		mMaxCanvasTranslate = 0.02f * mOutSideRadius;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		getRealTime();
		setCameraRotate(canvas);
		drawBackGroundColor(canvas);
		drawBGArea(canvas);
		drawScale(canvas);
		drawSecond(canvas);
		drawHourMinute(canvas);
		invalidate();
	}

	private void setCameraRotate(Canvas canvas) {
		mCameraMatrix.reset();
		float[] matrix = new float[9];
		mCameraMatrix.getValues(matrix);

		mCamera.save();
		mCamera.rotateX(mCameraRotateX);
		mCamera.rotateY(mCameraRotateY);
		mCamera.getMatrix(mCameraMatrix);
		mCamera.restore();

		//camera在view左上角那个点，故旋转默认是以左上角为中心旋转
		//故在动作之前pre将matrix向左移动getWidth()/2长度，向上移动getHeight()/2长度
		mCameraMatrix.preTranslate(-mCenterX, -mCenterY);
		//在动作之后post再回到原位
		mCameraMatrix.postTranslate(mCenterX, mCenterY);
		canvas.concat(mCameraMatrix); // A = A * B;
	}

	private void drawBackGroundColor(Canvas canvas) {
		canvas.drawRect(0, 0, getWidth(), getHeight(), mBackgroundPaint);
	}

	private void drawBGArea(Canvas canvas) {
		// 绘制时钟数字
		mTextPaint.getTextBounds("12", 0, "12".length(), mTextRect);
		canvas.drawText("12", mCenterX, mCenterY-mOutSideRadius+mTextRect.height()/2, mTextPaint);

		mTextPaint.getTextBounds("3", 0, "3".length(), mTextRect);
		canvas.drawText("3", mCenterX+mOutSideRadius, mCenterY+mTextRect.height()/2, mTextPaint);
		canvas.drawText("6", mCenterX, mCenterY+mOutSideRadius+mTextRect.height()/2, mTextPaint);
		canvas.drawText("9", mCenterX-mOutSideRadius, mCenterY+mTextRect.height()/2, mTextPaint);

		int anyAngle = (360-4*mSuitableTextAngle)/4;
		for (int i = 0; i < 4; i++) {
			canvas.drawArc(mCenterX-mOutSideRadius-1, mCenterY-mOutSideRadius-1, mCenterX+mOutSideRadius+1, mCenterY+mOutSideRadius+1, mSuitableTextAngle/2+90*i, anyAngle, false, mOutRadiusPaint);
		}
	}

	private void drawScale(Canvas canvas) {
		// 绘制渐变色扫射
		canvas.save();
		// canvas.translate(mCanvasTranslateX, mCanvasTranslateY);
		mSweepGradientMatrix.setRotate(mSweepDegree, mCenterX, mCenterY);
		mSweepGradient.setLocalMatrix(mSweepGradientMatrix);
		mSweepPaint.setShader(mSweepGradient);
		mSweepPaint.setStrokeWidth(0.15f*mOutSideRadius);
		int mSecondRadiusTmp = mSecondRadius+(int)(0.15f*mOutSideRadius)/2;
		canvas.drawArc(mCenterX-mSecondRadiusTmp, mCenterY-mSecondRadiusTmp, mCenterX+mSecondRadiusTmp, mCenterY+mSecondRadiusTmp, 0, 360, false, mSweepPaint);

		// 绘制尺度线
		for (int i = 0; i < 200; i++) {
			canvas.drawLine(mCenterX, mCenterY-mSecondRadius, mCenterX, mCenterY-mSecondRadius-(int)(0.15*mOutSideRadius), mSecondPaint);
			canvas.rotate(1.8f, mCenterX, mCenterY);
		}
		canvas.restore();
	}

	private void drawSecond(Canvas canvas) {
		// 绘制秒钟的三角标
		canvas.save();
		// canvas.translate(mCanvasTranslateX, mCanvasTranslateY);
		canvas.rotate(mSecondDegree, mCenterX, mCenterY); //以圆心旋转角度.
		if (mSecondTrianglePath.isEmpty()) {
			mSecondTrianglePath.reset();
			float line = 0.15f*mSecondRadius;
			mSecondTrianglePath.moveTo(mCenterX, mCenterY-mSecondRadius*0.96f);
			mSecondTrianglePath.lineTo(mCenterX-line/2, (float) (mCenterY-mSecondRadius*0.96f+Math.sqrt(3f)*line/2));
			mSecondTrianglePath.lineTo(mCenterX+line/2, (float) (mCenterY-mSecondRadius*0.96f+Math.sqrt(3f)*line/2));
			mSecondTrianglePath.close();
		}
		canvas.drawPath(mSecondTrianglePath, mSecondPaint);
		canvas.restore();
	}

	private void drawHourMinute(Canvas canvas) {
		// 绘制时标
		canvas.save();
		// canvas.translate(mCanvasTranslateX, mCanvasTranslateY);
		canvas.rotate(mHourDegree, mCenterX, mCenterY);
		if (mHourMarkPath.isEmpty()) {
			mHourMarkPath.reset();
			mHourMarkPath.moveTo(mCenterX-mMinuteRadius*0.03f, mCenterY);
			mHourMarkPath.lineTo(mCenterX-mMinuteRadius*0.015f, mCenterY-mHourRadius*0.8f);
			mHourMarkPath.quadTo(mCenterX, mCenterY-mHourRadius*0.9f, mCenterX+mMinuteRadius*0.015f, mCenterY-mHourRadius*0.8f);
			mHourMarkPath.lineTo(mCenterX+0.03f*mMinuteRadius, mCenterY);
			mHourMarkPath.close();
		}
		canvas.drawPath(mHourMarkPath, mHourPaint);
		canvas.restore();

		// 绘制分标
		canvas.save();
		// canvas.translate(mCanvasTranslateX, mCanvasTranslateY);
		canvas.rotate(mMinuteDegree, mCenterX, mCenterY);

		if (mMinuteMarkPath.isEmpty()) {
			mMinuteMarkPath.reset();
			mMinuteMarkPath.moveTo(mCenterX, mCenterY-mHourRadius*0.03f);
			mMinuteMarkPath.lineTo(mCenterX+mMinuteRadius*0.8f, mCenterY-mHourRadius*0.015f);
			mMinuteMarkPath.quadTo(mCenterX+mMinuteRadius*0.9f, mCenterY, mCenterX+mMinuteRadius*0.8f, mCenterY+mHourRadius*0.015f);
			mMinuteMarkPath.lineTo(mCenterX, mCenterY+mHourRadius*0.03f);
			mMinuteMarkPath.close();
		}
		canvas.drawPath(mMinuteMarkPath, mMinutePaint);
		canvas.restore();

		// 绘制圆心区小圆
		mBackgroundPaint.setColor(mMinuteMaskColor);
		canvas.drawCircle(mCenterX, mCenterY, mMinuteRadius*0.03f, mBackgroundPaint);

		mBackgroundPaint.setColor(mCenterCircleColor);
		canvas.drawCircle(mCenterX, mCenterY, mMinuteRadius*0.015f, mBackgroundPaint);
	}

	private void getRealTime() {
		// 获取当前h/m/s/mills及角度.
		Calendar calendar = Calendar.getInstance();
		mCurrMills = calendar.get(Calendar.MILLISECOND);
		mCurrSeconds = calendar.get(Calendar.SECOND)+mCurrMills/1000;
		mCurrMinute = calendar.get(Calendar.MINUTE) + mCurrSeconds/60;
		mCurrHour = calendar.get(Calendar.HOUR) + mCurrMinute/60;

		mHourDegree = mCurrHour/12*360;
		mMinuteDegree = mCurrMinute/60*360-90; // 因draw时是偏移了90°
		mSecondDegree = mCurrSeconds/60*360;
		mSweepDegree = mSecondDegree-90;  		 // 因draw时是偏移了90°
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);

		switch (event.getActionMasked()) {
			case MotionEvent.ACTION_DOWN:
				if (mShakeAnim != null && mShakeAnim.isRunning()) {
					mShakeAnim.cancel();
				}
			case MotionEvent.ACTION_MOVE:
				getCameraRotate(event);
				getCanvasTranslate(event);
				break;
			case MotionEvent.ACTION_UP:
				startShakeAnim(); // 自定义属性部分.
				break;
		}

		return true;
	}

	private float[] getPercent(float offsetX, float offsetY) {
		float[] result = new float[2];
		float nx = offsetX / mOutSideRadius;
		float ny = offsetY / mOutSideRadius;

		if (nx >= 1) {
			nx = 1;
		} else if (nx <= -1) {
			nx = -1;
		}

		if (ny >= 1) {
			ny = 1;
		} else if (ny <= -1) {
			ny = -1;
		}

		result[0] = nx;
		result[1] = ny;

		return result;
	}

	private void getCameraRotate(MotionEvent event) {
		float rotateX = -(event.getY() - mCenterY);
		float rotateY = (event.getX() - mCenterX);
		float[] result = getPercent(rotateX, rotateY);
		mCameraRotateX = result[0] * MAX_CAMER_ROTATE;
		mCameraRotateY = result[1] * MAX_CAMER_ROTATE;
	}

	private void getCanvasTranslate(MotionEvent event) {
		float rotateX = -(event.getX() - mCenterX);
		float rotateY = -(event.getY() - mCenterY);
		float[] result = getPercent(rotateX, rotateY);
		mCanvasTranslateX = result[0] * mMaxCanvasTranslate;
		mCanvasTranslateY = result[1] * mMaxCanvasTranslate;
	}

	private void startShakeAnim() {
		final String cameraRotateXName = "cameraRotateX";
		final String cameraRotateYName = "cameraRotateY";
		final String canvasTranslateXName = "canvasTranslateX";
		final String canvasTranslateYName = "canvasTranslateY";

		PropertyValuesHolder cameraRotateXHolder = PropertyValuesHolder.ofFloat(cameraRotateXName, mCameraRotateX, 0);
		PropertyValuesHolder cameraRotateYHolder = PropertyValuesHolder.ofFloat(cameraRotateYName, mCameraRotateY, 0);
		PropertyValuesHolder canvasTranslateXHolder = PropertyValuesHolder.ofFloat(canvasTranslateXName, mCanvasTranslateX, 0);
		PropertyValuesHolder canvasTranslateYHolder = PropertyValuesHolder.ofFloat(canvasTranslateYName, mCanvasTranslateY, 0);

		mShakeAnim = ValueAnimator.ofPropertyValuesHolder(cameraRotateXHolder, cameraRotateYHolder, canvasTranslateXHolder, canvasTranslateYHolder);
		mShakeAnim.setDuration(1000);
		mShakeAnim.setInterpolator(new TimeInterpolator() {
			@Override
			public float getInterpolation(float input) {
				//http://inloop.github.io/interpolator/ 动画函数.
				float f = 0.571429f;
				return (float) (Math.pow(2, -2 * input) * Math.sin((input - f / 4) * (2 * Math.PI) / f) + 1); // 比自带的BounceInterpolation效果要好.
			}
		});
		mShakeAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				if (animation.getAnimatedValue(cameraRotateXName) != null) {
					mCameraRotateX = (float) animation.getAnimatedValue(cameraRotateXName);
				}
				if (animation.getAnimatedValue(cameraRotateYName) != null) {
					mCameraRotateY = (float) animation.getAnimatedValue(cameraRotateYName);
				}
				if (animation.getAnimatedValue(canvasTranslateXName) != null) {
					mCanvasTranslateX = (float) animation.getAnimatedValue(canvasTranslateXName);
				}
				if (animation.getAnimatedValue(canvasTranslateYName) != null) {
					mCanvasTranslateY = (float) animation.getAnimatedValue(canvasTranslateYName);
				}
			}
		});
		mShakeAnim.start();
	}
}
