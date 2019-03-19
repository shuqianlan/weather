package com.ilifesmart.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.GradientDrawable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.Log;

public class MyConstrainLayout extends ConstraintLayout {

	public static final String TAG = "MyConstrainLayout";
	private Paint mQualPaint;
	private Path  mQualTopPath;
	private Path  mQualBtmPath;

	private int width;
	private int height;

	public MyConstrainLayout(Context context) {
		this(context, null);
	}

	public MyConstrainLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MyConstrainLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		initialize();
	}

	private void initialize() {
		mQualPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mQualPaint.setColor(Color.parseColor("#33FFFFFF"));

		mQualTopPath = new Path();
		mQualBtmPath = new Path();
		setBackground(new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[]{Color.parseColor("#FFD4C3FC"), Color.parseColor("#FF7697F9")}));
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		width = getWidth();
		height = getHeight();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		canvas.save();
		synchronized (this) {
			Log.d(TAG, "onDraw: >>>>>>>> 1");
			if (mQualBtmPath.isEmpty()) {
				mQualBtmPath.reset();
				mQualBtmPath.moveTo(0, 0.9f*height);
				mQualBtmPath.quadTo(0.2f*width, 0.78f*height, 0.37f*width, 0.85f*height);
				mQualBtmPath.quadTo(0.49f*width, 0.9f*height, 0.61f*width, 0.85f*height);
				mQualBtmPath.quadTo(0.76f*width, 0.81f*height, width, 0.9f*height);
				mQualBtmPath.lineTo(width, height);
				mQualBtmPath.lineTo(0, height);
				mQualBtmPath.close();
			}

			canvas.drawPath(mQualBtmPath, mQualPaint);

			if (mQualTopPath.isEmpty()) {
				mQualTopPath.reset();
				mQualTopPath.moveTo(0, 0.75f*height);
				mQualTopPath.quadTo(0.20f*width, 0.94f*height, 0.41f*width, 0.8f*height);
				mQualTopPath.quadTo(0.54f*width, 0.73f*height, 0.67f*width, 0.8f*height);
				mQualTopPath.quadTo(0.82f*width, 0.87f*height, width, 0.75f*height);
				mQualTopPath.lineTo(width, height);
				mQualTopPath.lineTo(0, height);
				mQualTopPath.close();
			}
			canvas.drawPath(mQualTopPath, mQualPaint);
		}

		canvas.restore();
	}
}
