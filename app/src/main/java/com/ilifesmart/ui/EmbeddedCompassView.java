package com.ilifesmart.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import com.ilifesmart.weather.R;

public class EmbeddedCompassView extends View {
	public interface OnAngleChangeListener {
		void onTouchBegin();
		void onAngleChanged(double angle, EmbeddedCompassView compass);//[0~360]
		void onTouchEnd();
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
	private float validBeginPercent = 0.3f;
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
			validBeginPercent = arr.getFloat(R.styleable.EmbeddedCompassView_validBeginPercent, validBeginPercent);
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
		mArrowPath.reset();
		mArrowPath.moveTo(positionX-mStickRadius+arrowB, positionY-arrowA);
		mArrowPath.lineTo(positionX-mStickRadius+arrowA, positionY);
		mArrowPath.lineTo(positionX-mStickRadius+arrowB, positionY+arrowA);
		canvas.drawPath(mArrowPath, mOutSideCirclePaint);

		// right
		mArrowPath.reset();
		mArrowPath.moveTo(positionX+mStickRadius-arrowB, positionY-arrowA);
		mArrowPath.lineTo(positionX+mStickRadius-arrowA, positionY);
		mArrowPath.lineTo(positionX+mStickRadius-arrowB, positionY+arrowA);
		canvas.drawPath(mArrowPath, mOutSideCirclePaint);

		//top
		mArrowPath.reset();
		mArrowPath.moveTo(positionX-arrowA, positionY-mStickRadius+arrowB);
		mArrowPath.lineTo(positionX, positionY-mStickRadius+arrowA);
		mArrowPath.lineTo(positionX+arrowA, positionY-mStickRadius+arrowB);
		canvas.drawPath(mArrowPath, mOutSideCirclePaint);

		//bottom
		mArrowPath.reset();
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
				if (listener != null) {
					listener.onTouchBegin();
				}
				break;
			case MotionEvent.ACTION_MOVE:
			case MotionEvent.ACTION_HOVER_MOVE:
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
				if (listener != null) {
					listener.onTouchEnd();
				}
				break;
		}

		if (isSticking) {
			invalidate();

			if (listener != null) {
				double dist = Math.sqrt((positionX - mCenterX) * (positionX - mCenterX) + (positionY - mCenterY) * (positionY - mCenterY));
				double angle = Math.acos((positionX-mCenterX)/dist)*180.0/Math.PI;
				Log.d(TAG, "onTouchEvent: angle " + angle);

				if (positionY > mCenterY) {
					angle = 360 - angle;
				}

				if (validBeginPercent <= ((float)dist)/(mThresholdRadius-mStickRadius)) {
					listener.onAngleChanged(angle, this); // [0-360] [0:右, 90:上，180:左，270:下
				}
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

	public static class ORIENTATION{
		public static final int L  = 0;
		public static final int T  = 1;
		public static final int R  = 2;
		public static final int B  = 3;
		public static final int LT = 4;
		public static final int RT = 5;
		public static final int RB = 6;
		public static final int LB = 7;

		public static String getDisp(int orientation) {
			String result = null;
			switch (orientation) {
				case 0:
					result = "Left";
					break;
				case 1:
					result = "Top";
					break;
				case 2:
					result = "Right";
					break;
				case 3:
					result = "Bottom";
					break;
				case 4:
					result = "Left-Top";
					break;
				case 5:
					result = "Right-Top";
					break;
				case 6:
					result = "Right-Bottom";
					break;
				case 7:
					result = "Left-Bottom";
					break;
				default:
					result = "unknown orientation.";
			}
			return result;
		}
	}

	// 四个方向
	public int getOrientationQuad(double angle) {
		int result = ORIENTATION.L;

		if (angle <= 45 && angle >= 315) {
			result = ORIENTATION.R;
		} else if (45 < angle && angle <= 135) {
			result = ORIENTATION.T;
		} else if (135 < angle && angle <= 225) {
			result = ORIENTATION.L;
		} else if (225 < angle && angle < 315) {
			result = ORIENTATION.B;
		}

		Log.d(TAG, "getOrientationQuad: Orientation:" + ORIENTATION.getDisp(result));
		return result;
	}

	// 八个方向
	public int getOrientationOctag(double angle) {
		int result = ORIENTATION.L;

		if (22.5 < angle && angle <= 67.5) {
			result = ORIENTATION.RT;
		} else if (67.5 < angle && angle <= 112.5) {
			result = ORIENTATION.T;
		} else if (112.5 < angle && angle <= 157.5) {
			result = ORIENTATION.LT;
		} else if (157.5 < angle && angle <= 202.5) {
			result = ORIENTATION.L;
		} else if (202.5 < angle && angle <= 247.5) {
			result = ORIENTATION.LB;
		} else if (247.5 < angle && angle <= 292.5) {
			result = ORIENTATION.B;
		} else if (292.5 < angle && angle <= 337.5) {
			result = ORIENTATION.RB;
		} else if (337.5 < angle || angle <= 22.5) {
			result = ORIENTATION.R;
		}

		Log.d(TAG, "getOrientationOctag: Orientation:" + ORIENTATION.getDisp(result));
		return result;
	}
}
