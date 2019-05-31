package com.ilifesmart.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public abstract class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {

	protected Context mContext;
	protected SurfaceHolder mHolder;

	protected MySurfaceView(Context context) {
		this(context, null);
	}

	protected MySurfaceView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	protected MySurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		mContext = context;
		mHolder = getHolder();
		mHolder.addCallback(this);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		onSurfaceCreated(holder);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		onSurfaceChanged(holder, format, width, height);
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		mHolder.removeCallback(this);
		onSurfaceDestroyed(holder);
	}

	public void onSurfaceCreated(SurfaceHolder holder) {}

	public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height){}

	public void onSurfaceDestroyed(SurfaceHolder holder){}

}
