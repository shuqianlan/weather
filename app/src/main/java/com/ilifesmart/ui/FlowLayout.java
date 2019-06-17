package com.ilifesmart.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class FlowLayout extends ViewGroup {

	public static final String TAG = "FlowLayout";

	public FlowLayout(Context context) {
		this(context, null);
	}

	public FlowLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);

		int rows = 1;

		boolean isBrokeLine = false;
		int childCount = getChildCount();

		int width = 0;
		int height = 0;
		int maxChildHeight = 0;
		int maxWidthPerRow = 0;

		for (int index = 0; index < childCount; index++) {
			View child = getChildAt(index);

			measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);

			MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
			int childWidth = lp.leftMargin + lp.rightMargin + child.getMeasuredWidth();
			int childHeight = lp.topMargin + lp.bottomMargin + child.getMeasuredHeight();

			if (childWidth > widthSize) {
				width = 0;
				height += childHeight;
			} else {
				if ((width + childWidth) > widthSize) {
					height += childHeight;
				} else {
					width += childWidth;
				}
			}

		}

		Log.d(TAG, "onMeasure: width " + widthSize + " height " + height);
		setMeasuredDimension(widthSize, height);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int childCount = getChildCount();
		int left = getPaddingLeft();
		int top = getPaddingTop();
		int width = r-l;
		int lastHeight = 0;

		for (int index = 0; index < childCount; index++) {
			View child = getChildAt(index);

			MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

			left += lp.leftMargin;
			top  += lp.topMargin;
			int childWidth = lp.leftMargin + lp.rightMargin + child.getMeasuredWidth();
			int childHeight= lp.topMargin + lp.bottomMargin + child.getMeasuredHeight();

			if ( (left + childWidth) > width ) {
				left = getPaddingLeft() + lp.leftMargin;
				top += lastHeight;
			}
			child.layout(left, top, left+childWidth, top+childHeight);

			top -= lp.topMargin;
			left += childWidth - lp.leftMargin;
			lastHeight = childHeight;
		}
	}

	@Override
	public LayoutParams generateLayoutParams(AttributeSet attrs) {
		return new MarginLayoutParams(getContext(), attrs);
	}

	@Override
	protected LayoutParams generateLayoutParams(LayoutParams p) {
		return new MarginLayoutParams(p);
	}

	@Override
	protected LayoutParams generateDefaultLayoutParams() {
		return new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	}
}
