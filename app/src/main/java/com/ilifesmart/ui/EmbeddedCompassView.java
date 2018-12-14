package com.ilifesmart.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.ilifesmart.weather.R;

import java.util.Arrays;

public class EmbeddedCompassView extends View {
	public interface OnAngleChangeListener {
		void onAngleChanged(double angle);//[0~pi]
	}

	private OnAngleChangeListener listener;
	public static final String TAG = "EmbededCompassView";
	private Paint mOutSideCirclePaint;
	private Paint mStickCirclePaint;

	private int mWidth;
	private int mHeight;
	private int mCenterX;
	private int mCenterY;
	private int mOutsideRadius;
	private int mThresholdRadius;
	private int mStickRadius;
	private int mOutSideRadiusStrokeColor;
	private int mArrowColor;
	private int mStickColor;
	private Path mArrowPath;

	public EmbeddedCompassView(Context context) {
		this(context, null);
	}

	public EmbeddedCompassView(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public EmbeddedCompassView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initialize(attrs);
	}

	private void initialize(AttributeSet attrs) {
		mOutSideCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mOutSideCirclePaint.setStyle(Paint.Style.STROKE);
		mOutSideCirclePaint.setStrokeWidth(4);

		mStickCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mStickCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);

		if (attrs != null) {
			TypedArray arr = getContext().obtainStyledAttributes(attrs, R.styleable.EmbeddedCompassView);
			mOutSideRadiusStrokeColor = arr.getColor(R.styleable.EmbeddedCompassView_outSideRadiusStrokerColor, Color.DKGRAY);
			mArrowColor = arr.getColor(R.styleable.EmbeddedCompassView_arrowColor, Color.DKGRAY);
			mStickColor = arr.getColor(R.styleable.EmbeddedCompassView_stickColor, Color.LTGRAY);

			arr.recycle();
		}
		mStickCirclePaint.setColor(mStickColor);
		mArrowPath = new Path();
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		mWidth = w;
		mHeight = h;
		int contW = w - getPaddingLeft() - getPaddingRight();
		int contH = h - getPaddingTop() - getPaddingBottom();
		mCenterX = getPaddingLeft() + contW/2;
		mCenterY = getPaddingTop() + contH/2;
		mOutsideRadius = (int)(Math.min(w, h) * 0.5 - 4);
		mThresholdRadius = (int) (mOutsideRadius * 0.98);
		mStickRadius = (int) (mOutsideRadius * 0.685);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		mOutSideCirclePaint.setColor(mOutSideRadiusStrokeColor);
		canvas.drawCircle(mCenterX, mCenterY, mOutsideRadius, mOutSideCirclePaint);

		if (isSticking) {
			int offsetMaxDistance = (mThresholdRadius-mStickRadius);
			int offsetCurr = Math.max(Math.abs(positionX-mCenterX), Math.abs(positionY-mCenterY));

			int alpha = Math.max((int)(255*offsetCurr*1.0/offsetMaxDistance), 10);
			mStickCirclePaint.setAlpha(alpha);
			canvas.drawCircle(positionX, positionY, mStickRadius, mStickCirclePaint);
			onDrawArrow(canvas, positionX, positionY);
		} else {
			onDrawArrow(canvas, mCenterX, mCenterY);
		}
	}

	private void onDrawArrow(Canvas canvas, int positionX, int positionY) {
		mOutSideCirclePaint.setColor(mArrowColor);
		int arrowA = 15;
		int arrowB = 30;

		// left
		mArrowPath.moveTo(positionX-mStickRadius+arrowB, positionY-arrowA);
		mArrowPath.lineTo(positionX-mStickRadius+arrowA, positionY);
		mArrowPath.lineTo(positionX-mStickRadius+arrowB, positionY+arrowA);
		canvas.drawPath(mArrowPath, mOutSideCirclePaint);

		// right
		mArrowPath.moveTo(positionX+mStickRadius-arrowB, positionY-arrowA);
		mArrowPath.lineTo(positionX+mStickRadius-arrowA, positionY);
		mArrowPath.lineTo(positionX+mStickRadius-arrowB, positionY+arrowA);
		canvas.drawPath(mArrowPath, mOutSideCirclePaint);

		//top
		mArrowPath.moveTo(positionX-arrowA, positionY-mStickRadius+arrowB);
		mArrowPath.lineTo(positionX, positionY-mStickRadius+arrowA);
		mArrowPath.lineTo(positionX+arrowA, positionY-mStickRadius+arrowB);
		canvas.drawPath(mArrowPath, mOutSideCirclePaint);

		//bottom
		mArrowPath.moveTo(positionX-arrowA, positionY+mStickRadius-arrowB);
		mArrowPath.lineTo(positionX, positionY+mStickRadius-arrowA);
		mArrowPath.lineTo(positionX+arrowA, positionY+mStickRadius-arrowB);
		canvas.drawPath(mArrowPath, mOutSideCirclePaint);
	}

	private int positionX;
	private int positionY;
	private boolean isSticking;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int downX = (int)event.getX();
		int downY = (int)event.getY();
		super.onTouchEvent(event);

		switch (event.getActionMasked()) {
			case MotionEvent.ACTION_DOWN:
				if (isInOutSideCircle(downX, downY)) {
					isSticking = true;
					positionX = downX;
					positionY = downY;
				}
				break;
			case MotionEvent.ACTION_MOVE:
				if (isSticking) {
					if (isInOutSideCircle(downX, downY)) {
						positionX = downX;
						positionY = downY;
					} else {
						double dist = Math.sqrt((downX - mCenterX) * (downX - mCenterX) + (downY - mCenterY) * (downY - mCenterY));
						positionX = (int) (((downX - mCenterX) * (mThresholdRadius - mStickRadius)) / dist + mCenterX);
						positionY = (int) (((downY - mCenterY) * (mThresholdRadius - mStickRadius)) / dist + mCenterY);
					}
				}
				break;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_CANCEL:
				if (isSticking) {
					isSticking = false;
					invalidate();
				}
				break;
		}

		if (isSticking) {
			invalidate();

			if (listener != null) {
				double dist = Math.sqrt((positionX - mCenterX) * (positionX - mCenterX) + (positionY - mCenterY) * (positionY - mCenterY));
				double angle = Math.acos((positionX-mCenterX)/dist);
				listener.onAngleChanged(angle);
			}
		}
		return true;
	}

	// 判断是否落在圆内.
	private boolean isInOutSideCircle(int positionX, int positionY) {
		int pow2 = (int)( Math.pow((positionX-mCenterX), 2) + Math.pow((positionY-mCenterY), 2));
		int distance = (int) Math.sqrt(pow2);
		return ((distance+mStickRadius) <= mThresholdRadius);
	}

	public OnAngleChangeListener getListener() {
		return listener;
	}

	public EmbeddedCompassView setListener(OnAngleChangeListener listener) {
		this.listener = listener;
		return this;
	}
}
