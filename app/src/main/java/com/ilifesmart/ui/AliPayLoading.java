package com.ilifesmart.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.util.Log;

import java.util.concurrent.atomic.AtomicInteger;

public class AliPayLoading extends CustomSurfaceView {

	private Path mPath;
	private Path mCirclePath;
	private Path mTickPath;
	private Path mDstPath;

	private int  mPivotX;
	private int  mPivotY;
	private float mRadius;

	private float mTotalLength;
	private float mCircleLength;
	private float mTickLength;


	private PathMeasure mPathMeasure;

	public AliPayLoading(Context context) {
		this(context, null);
	}

	public AliPayLoading(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public AliPayLoading(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		initialize();
	}

	private void initialize() {
		mPath = new Path();
		mCirclePath = new Path();
		mTickPath = new Path();
		mDstPath = new Path();
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		mRadius = Math.min(w, h)/2.0f;
		int contentWidth  = w - getPaddingLeft() - getPaddingRight();
		int contentHeight = h - getPaddingTop() - getPaddingBottom();
		mPivotX = getPaddingLeft() + contentWidth/2;
		mPivotY = getPaddingTop() + contentHeight/2;

		mCirclePath.reset();
		mCirclePath.addCircle(mPivotX, mPivotY, mRadius, Path.Direction.CW);

		mTickPath.reset();
		mTickPath.moveTo(mPivotX-mRadius/2, mPivotY);
		mTickPath.lineTo(mPivotX, mPivotY+mRadius/2);
		mTickPath.lineTo(mPivotX+mRadius/2, mPivotY-mRadius/2);

		mPath.reset();
		mPath.addPath(mCirclePath);
		mPath.addPath(mTickPath);

		mPathMeasure = new PathMeasure(mCirclePath, true);
		mCircleLength = mPathMeasure.getLength();

		mPathMeasure = new PathMeasure(mTickPath, true);
		mTickLength = mPathMeasure.getLength();

		mPathMeasure = new PathMeasure(mPath, false);
		mTotalLength = mPathMeasure.getLength();
	}

	private AtomicInteger delay = new AtomicInteger(0);
	private float progress = 0;

	private boolean isNextContour = false;

	@Override
	public void doDraw(Canvas canvas, Paint paint) {
		long doDrawMills = System.currentTimeMillis();
		canvas.drawColor(Color.BLACK);
		paint.setStyle(Paint.Style.STROKE);
		paint.setFlags(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.WHITE);

		if (progress <= 1) {
			float length = mPathMeasure.getLength();
			float distance = (progress * length);
			mPathMeasure.getSegment(0, distance, mDstPath, true);
		} else {
			if (!isNextContour) {
				isNextContour = mPathMeasure.nextContour(); //转移到下一个路径.
			}

			if (isNextContour) {
				float length = mPathMeasure.getLength();
				float distance = (progress-1)*length;
				mPathMeasure.getSegment(0, distance, mDstPath, true);
			}
		}

		canvas.drawPath(mDstPath, paint);

		try {
			if (progress <= 0.0000001) {
				Log.d("TimeTag", "doDraw: start...");
			} else if (progress > 2.000001) {
				Log.d("TimeTag", "doDraw: end...");
			}

			Log.d("Surface", "doDraw: " + (System.currentTimeMillis() - doDrawMills));
			Thread.sleep(3);
			progress = ((delay.incrementAndGet())*4/200.0f);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}
