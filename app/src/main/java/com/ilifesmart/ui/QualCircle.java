package com.ilifesmart.ui;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.ilifesmart.weather.R;

public class QualCircle extends View {
	public static final String TAG = "QualCircle";

	private Paint mPathCirclePaint;
	private int mCenterX, mCenterY;
	private Path mQualCircle;
	private float mCircleParameter = 0.551915024494f;
	private int mRadius;
	private ValueAnimator mValueAnimator;
	private float mCircleMaxOffsetParameter = 0.96f;
	private Point[] mPoints = new Point[12];

	public QualCircle(Context context) {
		this(context, null);
	}

	public QualCircle(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public QualCircle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initizlize();
	}

	private void initizlize() {
		mPathCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPathCirclePaint.setStyle(Paint.Style.FILL);
		mPathCirclePaint.setColor(getResources().getColor(R.color.colorPrimary));

		mQualCircle = new Path();
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		int contW = w - getPaddingLeft() - getPaddingRight();
		int contH = h - getPaddingTop() - getPaddingBottom();
		mRadius = Math.min(contH, contW)/2;
		mCenterX = getPaddingLeft() + contW/2;
		mCenterY = getPaddingTop() + contH/2;

		mPoints[0] = new Point(mCenterX, mCenterY+mRadius);
		mPoints[1] = new Point((int) (mCenterX+mRadius*mCircleParameter), mCenterY+mRadius);
		mPoints[2] = new Point((int) (mCenterX+mRadius), mCenterY+(int) (mRadius*mCircleParameter));
		mPoints[3] = new Point((int) (mCenterX+mRadius), mCenterY);
		mPoints[4] = new Point((int) (mCenterX+mRadius), mCenterY-(int) (mRadius*mCircleParameter));
		mPoints[5] = new Point((int) (mCenterX+mRadius*mCircleParameter), mCenterY-mRadius);
		mPoints[6] = new Point((int) (mCenterX), mCenterY-mRadius);
		mPoints[7] = new Point((int) (mCenterX-mRadius*mCircleParameter), mCenterY-mRadius);
		mPoints[8] = new Point((int) (mCenterX-mRadius), (int)(mCenterY-mRadius*mCircleParameter));
		mPoints[9] = new Point((int) (mCenterX-mRadius), (int)(mCenterY));
		mPoints[10] = new Point((int) (mCenterX-mRadius), (int)(mCenterY+mRadius*mCircleParameter));
		mPoints[11] = new Point((int) (mCenterX-mRadius*mCircleParameter), (int)(mCenterY+mRadius));
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		// Path绘制圆
		mQualCircle.reset();
		mQualCircle.moveTo(mPoints[0].x, mPoints[0].y);
		for (int i = 1; i < 11; i+=3) {
			mQualCircle.cubicTo(mPoints[i].x, mPoints[i].y, mPoints[i+1].x, mPoints[i+1].y, mPoints[(i+2)%12].x, mPoints[(i+2)%12].y);
		}

		canvas.drawPath(mQualCircle, mPathCirclePaint);
	}

	public void startAnimation() {
		mValueAnimator = ValueAnimator.ofFloat(0, 16);
		mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			float lastNumber = 0;
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				if (animation.getAnimatedValue() != null) {
					float i = (float)animation.getAnimatedValue();

					Log.d(TAG, "onAnimationUpdate: index " + i);

					if (i < 4) {
						mPoints[2].x += (i-lastNumber)/4*mCircleMaxOffsetParameter*mRadius;
						mPoints[3].x += (i-lastNumber)/4*mCircleMaxOffsetParameter*mRadius;
						mPoints[4].x += (i-lastNumber)/4*mCircleMaxOffsetParameter*mRadius;
					} else if (i < 8) {
						mPoints[8].x -= (i-4-lastNumber)/4*mCircleMaxOffsetParameter*mRadius;
						mPoints[9].x -= (i-4-lastNumber)/4*mCircleMaxOffsetParameter*mRadius;
						mPoints[10].x -= (i-4-lastNumber)/4*mCircleMaxOffsetParameter*mRadius;
					} else if (i < 12) {
						mPoints[2].x -= (i-8-lastNumber)/4*mCircleMaxOffsetParameter*mRadius;
						mPoints[3].x -= (i-8-lastNumber)/4*mCircleMaxOffsetParameter*mRadius;
						mPoints[4].x -= (i-8-lastNumber)/4*mCircleMaxOffsetParameter*mRadius;
					} else if (i < 16) {
						mPoints[8].x += (i-12-lastNumber)/4*mCircleMaxOffsetParameter*mRadius;
						mPoints[9].x += (i-12-lastNumber)/4*mCircleMaxOffsetParameter*mRadius;
						mPoints[10].x += (i-12-lastNumber)/4*mCircleMaxOffsetParameter*mRadius;
					}
					lastNumber = i;
					invalidate();
				}
			}
		});
		mValueAnimator.setDuration(16000);
		mValueAnimator.start();
	}

	public void stopAnimation() {
		if (mValueAnimator != null && mValueAnimator.isRunning()) {
			mValueAnimator.cancel();
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return super.onTouchEvent(event);
	}
}
