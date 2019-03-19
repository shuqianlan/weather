package com.ilifesmart.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.ilifesmart.weather.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SpiderWeb extends View implements GestureDetector.OnGestureListener {
	public static final String TAG = "SpiderWeb";

	private int DEFAULT_MAX_POINTS_OFFSET = 200; // 两点间画线的最大距离
	private int DEFAULT_POINT_ALPHA = 150;       // 线的最大alpha
	private int DEFAULT_POINT_COUNTS = 30;       // 随机点数量
	private int DEFAULT_ACCELATION_STEP = 5;     // 步进偏移
	private int DEFAULT_STROKE_WIDTH_LINE = 10;
	private int DEFAULT_STROKE_WIDTH_POINT = 10;
	private int DEFAULT_STROKE_WIDTH_TOUCH_POINT = 10;
	private int DEFAULT_LINE_COLOR = Color.RED;
	private int DEFAULT_POINT_COLOR = Color.RED;
	private int DEFAULT_TOUCH_POINT_COLOR = Color.RED;

	private Paint mPointPaint;
	private Paint mLinePaint;
	private Paint mTouchPaint;

	private Random mRandom;
	private Config mConfig;

	private int mWidth;
	private int mHeight;

	private int mTouchX = -1;
	private int mTouchY = -1;

	private class Config {
		public int mLineStrokeWidth = DEFAULT_STROKE_WIDTH_LINE;
		public int mPointStrokeWidth = DEFAULT_STROKE_WIDTH_POINT;
		public int mTouchPointStrokeWidth = DEFAULT_STROKE_WIDTH_TOUCH_POINT;
		public int mDotsMaxDistance = DEFAULT_MAX_POINTS_OFFSET;
		public int mDotsCount = DEFAULT_POINT_COUNTS;
		public int mLineMaxAlpha = DEFAULT_POINT_ALPHA;
		public int mLineColor = DEFAULT_LINE_COLOR;
		public int mPointColor = DEFAULT_POINT_COLOR;
		public int mTouchPointColor = DEFAULT_TOUCH_POINT_COLOR;
	}

	private GestureDetector mGestureDetector;
	private List<CodePoint> mPointList = new ArrayList<>();

	public SpiderWeb(Context context) {
		this(context, null);
	}

	public SpiderWeb(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SpiderWeb(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		initialize(attrs);
	}

	private void initPaint() {
		mPointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPointPaint.setColor(mConfig.mPointColor);
		// Paint函数大集合: https://blog.csdn.net/harvic880925/article/details/51010839
		mPointPaint.setStrokeCap(Paint.Cap.ROUND); // 设置笔刷类型
		mPointPaint.setStrokeWidth(mConfig.mPointStrokeWidth);

		mLinePaint = new Paint();
		mLinePaint.setColor(mConfig.mLineColor);
		mLinePaint.setStrokeCap(Paint.Cap.ROUND);
		mLinePaint.setStrokeWidth(mConfig.mLineStrokeWidth);

		mTouchPaint = new Paint();
		mTouchPaint.setColor(mConfig.mTouchPointColor);
		mTouchPaint.setStrokeCap(Paint.Cap.ROUND);
		mTouchPaint.setStrokeWidth(mConfig.mTouchPointStrokeWidth);
	}

	private void initialize(AttributeSet attrs) {
		mConfig = new Config();

		if (attrs != null) {
			TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.SpiderWeb);
			mConfig.mDotsCount = array.getInt(R.styleable.SpiderWeb_dotsCount, mConfig.mDotsCount);
			array.recycle();
		}

		initPaint();
		mGestureDetector = new GestureDetector(getContext(), this);
		mRandom = new Random();
	}

	private void initCodePoints() {
		mPointList.clear();
		for (int i = 0; i < DEFAULT_POINT_COUNTS; i++) {
			int point_x = (int)(mRandom.nextDouble() * mWidth);  // 初始x屏幕内
			int point_y = (int)(mRandom.nextDouble() * mHeight); // 初始y屏幕内
			int xa = 0;
			int ya = 0;

			while (xa == 0) {
				xa = (int) ((mRandom.nextDouble() - 0.5) * DEFAULT_ACCELATION_STEP);
			}

			while (ya == 0) {
				ya = (int) ((mRandom.nextDouble() - 0.5) * DEFAULT_ACCELATION_STEP);
			}

			mPointList.add(new CodePoint(point_x, point_y, xa, ya));
		}
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		mWidth = w;
		mHeight = h;

		initCodePoints();
		Log.d(TAG, "onSizeChanged: pointSize " + mPointList.size());
	}

	@Override
	protected void onDraw(Canvas canvas) {

		if (mTouchY != -1 && mTouchX != -1) {
			canvas.drawPoint(mTouchX, mTouchY, mPointPaint);
		}

		for (CodePoint currPoint:mPointList) {
			currPoint.x += currPoint.getXa();
			currPoint.y += currPoint.getYa();

			if (mTouchX != -1 && mTouchY != -1) {
				drawLine(canvas, currPoint, new CodePoint(mTouchX, mTouchY), mTouchPaint);
			}

			if (currPoint.x >= mWidth || currPoint.x <= 0) {
				currPoint.setXa(-currPoint.getXa());
			}

			if (currPoint.y >= mHeight || currPoint.y <= 0) {
				currPoint.setYa(-currPoint.getYa());
			}

			canvas.drawPoint(currPoint.x, currPoint.y, mPointPaint);

			for (CodePoint point:mPointList) {
				if (point != currPoint) {
					drawLine(canvas, currPoint, point, mLinePaint);
				}
			}
		}

		postInvalidate();
	}

	private void drawLine(Canvas canvas, CodePoint currPoint, CodePoint point, Paint paint) {
		int offsetX = currPoint.x - point.x;
		int offsetY = currPoint.y - point.y;
		int distance = (int) Math.sqrt(offsetX *offsetX + offsetY * offsetY); // offsetX^2; ^是异或的意思

		int alpha = (int)((1.0 - distance/(double)mConfig.mDotsMaxDistance) * mConfig.mLineMaxAlpha);
		if (distance <= mConfig.mDotsMaxDistance && alpha > 0) {
			mLinePaint.setAlpha(alpha);
			canvas.drawLine(currPoint.x, currPoint.y, point.x, point.y, paint);
		}
	}

	private void clearTouchPoint() {
		mTouchX = -1;
		mTouchY = -1;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_UP) {
			clearTouchPoint();
			return true;
		}
		return mGestureDetector.onTouchEvent(event);
	}

	@Override
	public boolean onDown(MotionEvent e) {
		mTouchX = (int)e.getX();
		mTouchY = (int)e.getY();
		return true;
	}

	@Override
	public void onShowPress(MotionEvent e) {

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		mTouchX = (int) e2.getX();
		mTouchY = (int) e2.getY();
		return true;
	}

	@Override
	public void onLongPress(MotionEvent e) { }

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		mTouchX = (int)e2.getX();
		mTouchY = (int)e2.getY();
		return true;
	}

	private class CodePoint extends Point {
		private int xa;
		private int ya;

		public CodePoint(int x, int y) {
			super(x, y);
		}

		public CodePoint(int x, int y, int xa, int ya) {
			super(x, y);

			this.xa = xa;
			this.ya = ya;
		}

		public int getXa() {
			return xa;
		}

		public void setXa(int xa) {
			this.xa = xa;
		}

		public int getYa() {
			return ya;
		}

		public void setYa(int ya) {
			this.ya = ya;
		}

		@Override
		public String toString() {
			return "[ " + "x: " + x + "; y: " + y + "; xa: " + xa + "; ya: " + ya + "]";
		}
	}
}
