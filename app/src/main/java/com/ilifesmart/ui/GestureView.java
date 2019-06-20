package com.ilifesmart.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class GestureView extends View implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

	public static final String TAG = "GestureView";
	private GestureDetector mGestureDetector;

	public GestureView(Context context) {
		this(context, null);
	}

	public GestureView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public GestureView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		initialize(context, attrs);
	}

	private void initialize(Context context, AttributeSet attrs) {

		mGestureDetector = new GestureDetector(context, this);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return mGestureDetector.onTouchEvent(event);
	}

	@Override
	public boolean onDown(MotionEvent e) {
		Log.d(TAG, "onDown: ");
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		Log.d(TAG, "onShowPress: ");
		// 已触发按下尚未触发MOVE或UP事件，用于作为可视化的操作
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// UP事件触发
		Log.d(TAG, "onSingleTapUp: ");
		return false;
	}

	/*
	* onScroll
	* e1:Down事件的MotionEvent
	*	e2:MOVE事件的MotionEvent
	*	distanceX: 距离上次onScroll的偏移量
	*	distanceY: 距离上次onScroll的偏移量
	* 相对上次的滑动方向及基于Down点的滑动方向.
	*
	* */
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		Log.d(TAG, "onScroll: ");
		return false;
	}

	/* 长按 */
	@Override
	public void onLongPress(MotionEvent e) {
		Log.d(TAG, "onLongPress: ");
	}

	/*
	* onFling
	* e1: Down事件
	* e2: UP事件
	* velocityX：X轴滑动的速度(像素/s)
	* velocityY：Y轴滑动的速度(像素/s)
	* */
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		Log.d(TAG, "onFling: ");
		return false;
	}

	/*
	* 真-单击
	* */
	@Override
	public boolean onSingleTapConfirmed(MotionEvent e) {
		return false;
	}

	/*
	*
	* 双击的Down事件
	* */
	@Override
	public boolean onDoubleTap(MotionEvent e) {
		return false;
	}

	/*
	 * 完整的双击
	 * */
	@Override
	public boolean onDoubleTapEvent(MotionEvent e) {
		return false;
	}
}
