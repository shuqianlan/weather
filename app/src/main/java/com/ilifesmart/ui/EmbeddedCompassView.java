package com.ilifesmart.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.baidu.mapapi.map.Circle;
import com.ilifesmart.ToolsApplication;
import com.ilifesmart.weather.R;

public class EmbeddedCompassView extends View {

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

	public EmbeddedCompassView(Context context) {
		this(context, null);
	}

	public EmbeddedCompassView(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public EmbeddedCompassView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initialize();
	}

	private void initialize() {
		mOutSideCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mOutSideCirclePaint.setStyle(Paint.Style.STROKE);
		mOutSideCirclePaint.setStrokeWidth(4);
		mOutSideCirclePaint.setColor(Color.DKGRAY);

		mStickCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mStickCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);
		mStickCirclePaint.setColor(Color.LTGRAY);
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
		Path path = new Path();
		int arrowA = 15;
		int arrowB = 30;

		// left
		path.moveTo(positionX-mStickRadius+arrowB, positionY-arrowA);
		path.lineTo(positionX-mStickRadius+arrowA, positionY);
		path.lineTo(positionX-mStickRadius+arrowB, positionY+arrowA);
		canvas.drawPath(path, mOutSideCirclePaint);

		// right
		path.moveTo(positionX+mStickRadius-arrowB, positionY-arrowA);
		path.lineTo(positionX+mStickRadius-arrowA, positionY);
		path.lineTo(positionX+mStickRadius-arrowB, positionY+arrowA);
		canvas.drawPath(path, mOutSideCirclePaint);

		//top
		path.moveTo(positionX-arrowA, positionY-mStickRadius+arrowB);
		path.lineTo(positionX, positionY-mStickRadius+arrowA);
		path.lineTo(positionX+arrowA, positionY-mStickRadius+arrowB);
		canvas.drawPath(path, mOutSideCirclePaint);

		//bottom
		path.moveTo(positionX-arrowA, positionY+mStickRadius-arrowB);
		path.lineTo(positionX, positionY+mStickRadius-arrowA);
		path.lineTo(positionX+arrowA, positionY+mStickRadius-arrowB);
		canvas.drawPath(path, mOutSideCirclePaint);
	}

	private int positionX;
	private int positionY;
	private Point lastPoint;
	private boolean isSticking;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		Point mCurrPoint;
		super.onTouchEvent(event);
		switch (event.getActionMasked()) {
			case MotionEvent.ACTION_DOWN:
				if (isInOutSideCircle((int)event.getX(), (int)event.getY())) {
					isSticking = true;
				}
				break;
			case MotionEvent.ACTION_MOVE:
				break;
			default:
				isSticking=false;
		}

		if (isSticking) {
			if (isInOutSideCircle((int)event.getX(), (int)event.getY())) {
				mCurrPoint = getNearPosition((int) event.getX(), (int) event.getY());
				positionX = mCurrPoint.x;
				positionY = mCurrPoint.y;
				lastPoint = mCurrPoint;
			} else {
				positionX = lastPoint.x;
				positionY = lastPoint.y;
			}
		}
		invalidate();

		Log.d(TAG, "onTouchEvent: isSticking " + isSticking);
		return true;
	}

	private Point getNearPosition(int touchX, int touchY) {
		// TODO:获取合适的Point
		return new Point(touchX, touchY);
	}

	private boolean isInOutSideCircle(int positionX, int positionY) {
		int pow2 = (int)( Math.pow((positionX-mCenterX), 2) + Math.pow((positionY-mCenterY), 2));
		int distance = (int) Math.sqrt(pow2);
		return ((distance+mStickRadius) <= mThresholdRadius);
	}
}
