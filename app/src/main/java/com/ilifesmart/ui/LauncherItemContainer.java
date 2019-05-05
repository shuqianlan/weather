package com.ilifesmart.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class LauncherItemContainer extends LinearLayout {

	/*
	*
	* 1. 继承ViewGroup
	* 2. 重写onMeasure方法测量子控件及自身宽高->measureChildWithMargins测量宽高.
	* 3. 实现onLayout方法摆放控件 -> child.layout(定位XY).
	*
	* */

	public LauncherItemContainer(Context context) {
		this(context, null);
	}

	public LauncherItemContainer(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public LauncherItemContainer(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

	}

	public void addContainer( ViewGroup group) {
		addView(group);
	}
}
