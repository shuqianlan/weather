package com.ilifesmart.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.ilifesmart.utils.DensityUtils;

public class MiClockView extends View {

	public static final String TAG = "MiClockView";
	private int mMaxHourTextWidth;
	private int mSuitableTextAngle;
	private int mCenterX;
	private int mCenterY;
	private int mOutSideRadius;
	private int mHourRadius;
	private int mMinuteRadius;
	private int mSecondRadius;

	private Paint mOutRadiusPaint;
	private Paint mSecondPaint;
	private Paint mSweepPaint;
	private Paint mTextPaint;

	private int mDarkColor = Color.DKGRAY; //Color.parseColor("#80ffffff");
	private int mLightColor = Color.LTGRAY; //Color.parseColor("#ffffff");

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
		mOutRadiusPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mOutRadiusPaint.setStyle(Paint.Style.STROKE);
		mOutRadiusPaint.setStrokeWidth(2);
		mOutRadiusPaint.setColor(mLightColor);

		mSecondPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mSecondPaint.setStyle(Paint.Style.FILL);
		mSecondPaint.setColor(mLightColor);

		mSweepPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

		mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mTextPaint.setColor(mDarkColor);
		mTextPaint.setTextAlign(Paint.Align.CENTER);
		mTextPaint.setTextSize(DensityUtils.sp2px(getContext(), 24));
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
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Rect rect = new Rect();
		mTextPaint.getTextBounds("12", 0, "12".length(), rect);
		canvas.drawText("12", mCenterX, mCenterY-mOutSideRadius+rect.height()/2, mTextPaint);

		mTextPaint.getTextBounds("3", 0, "3".length(), rect);
		canvas.drawText("3", mCenterX+mOutSideRadius, mCenterY+rect.height()/2, mTextPaint);

		mTextPaint.getTextBounds("6", 0, "6".length(), rect);
		canvas.drawText("6", mCenterX, mCenterY+mOutSideRadius+rect.height()/2, mTextPaint);

		mTextPaint.getTextBounds("9", 0, "9".length(), rect);
		canvas.drawText("9", mCenterX-mOutSideRadius, mCenterY+rect.height()/2, mTextPaint);

		int anyAngle = (360-4*mSuitableTextAngle)/4;
		for (int i = 0; i < 4; i++) {
			canvas.drawArc(mCenterX-mOutSideRadius-1, mCenterY-mOutSideRadius-1, mCenterX+mOutSideRadius+1, mCenterY+mOutSideRadius+1, mSuitableTextAngle/2+90*i, anyAngle, false, mOutRadiusPaint);
		}

//		Path path = new Path();
//		path.moveTo(mCenterX, mCenterY);
//		path.lineTo(mCenterX-4, mCenterY);
//		path.lineTo(mCenterX+mMinuteRadius, mCenterY);
//		canvas.drawPath(path, mOutRadiusPaint);
//
//		path = new Path();
//		path.moveTo(mCenterX, mCenterY);
//		path.lineTo(mCenterX, mCenterY-4);
//		path.lineTo(mCenterX, mCenterY+mHourRadius);
//		canvas.drawPath(path, mOutRadiusPaint);

//		canvas.save();
		SweepGradient gradient = new SweepGradient(mCenterX, mCenterY, new int[]{mLightColor, 0xFFFFFFFF}, new float[] {0.75f, 1});
		mSweepPaint.setShader(gradient);

		mSweepPaint.setStrokeWidth(0.15f*mOutSideRadius);
		mSweepPaint.setStyle(Paint.Style.STROKE);
		mSweepPaint.setAlpha(25);
		int mSecondRadiusTmp = mSecondRadius+(int)(0.15f*mOutSideRadius)/2;
		canvas.drawArc(mCenterX-mSecondRadiusTmp, mCenterY-mSecondRadiusTmp, mCenterX+mSecondRadiusTmp, mCenterY+mSecondRadiusTmp, 0, 360, false, mSweepPaint);
//		canvas.restore();

		canvas.save();
		for (int i = 0; i < 200; i++) {
			canvas.drawLine(mCenterX, mCenterY-mSecondRadius, mCenterX, mCenterY-mSecondRadius-(int)(0.15*mOutSideRadius), mSecondPaint);
			canvas.rotate(1.8f, mCenterX, mCenterY);
		}
		canvas.restore();


	}
}
