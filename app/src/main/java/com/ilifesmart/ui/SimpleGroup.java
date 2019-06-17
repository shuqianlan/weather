package com.ilifesmart.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class SimpleGroup extends ViewGroup {

	public static final String TAG = "SimpleGroup";

	public SimpleGroup(Context context) {
		super(context);
	}

	public SimpleGroup(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public SimpleGroup(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);

		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);

		int height = 0;
		int maxWidth = 0;

		Log.d(TAG, "onMeasure: count " + getChildCount());
		for (int index = 0; index < getChildCount(); index++) {
			View child = getChildAt(index);
			measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);
			MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

			height += child.getMeasuredHeight() + lp.bottomMargin + lp.topMargin;
			Log.d(TAG, "onMeasure: height " + height);
			maxWidth = Math.max(maxWidth, child.getMeasuredWidth());
		}

		int nWidth = (widthMode == MeasureSpec.EXACTLY) ? widthSize : maxWidth;
		int nHeight= (heightMode == MeasureSpec.EXACTLY)? heightSize: height;
		setMeasuredDimension(nWidth, nHeight);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int top = 0;
		int count = getChildCount();

		Log.d(TAG, "onLayout: count " + count);
		for (int index = 0; index < count; index++) {
			View child = getChildAt(index);
			MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

			int height = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
			int width = child.getMeasuredWidth();
			child.layout(0, top, width, top + height); // 先onSizeChanged后onLayout

			top += height;
		}
	}

	@Override
	protected LayoutParams generateLayoutParams(LayoutParams p) {
		return new MarginLayoutParams(p);
	}

	@Override
	public LayoutParams generateLayoutParams(AttributeSet attrs) {
		return new MarginLayoutParams(getContext(), attrs);
	}

	@Override
	protected LayoutParams generateDefaultLayoutParams() {
		return new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
	}


}
