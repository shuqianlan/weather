package com.ilifesmart.ui;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SnowFaceView extends SurfaceView implements SurfaceHolder.Callback {

	private SurfaceHolder mHolder;
	private Context mContext;

	public SnowFaceView(Context context) {
		this(context, null);
	}

	public SnowFaceView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SnowFaceView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.mContext = context;
		initialize(context, attrs);
	}

	private void initialize(Context context, AttributeSet attrs) {
		mHolder = getHolder();
		mHolder.setFormat(PixelFormat.TRANSLUCENT);
		mHolder.addCallback(this);

		setZOrderOnTop(true);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);


	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {

	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {

	}
}
