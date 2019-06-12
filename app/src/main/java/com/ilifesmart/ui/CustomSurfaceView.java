package com.ilifesmart.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.SurfaceHolder;

public abstract class CustomSurfaceView extends MySurfaceView {
	private LoopThread thread;
	private boolean isDestroyed = false;

	public CustomSurfaceView(Context context) {
		this(context, null);
	}

	public CustomSurfaceView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CustomSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		thread = new LoopThread();

		isDestroyed= false;
	}

	@Override
	public void onSurfaceCreated(SurfaceHolder holder) {
		thread.isRunning = true;
		thread.start();
	}

	@Override
	public void onSurfaceDestroyed(SurfaceHolder holder) {
		thread.isRunning = false;
		isDestroyed = true;
		try {
			thread.join();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	public class LoopThread extends Thread {
		boolean isRunning;
		Paint paint;
		private final Surface mSurface;

		public LoopThread() {
			paint = new Paint(Paint.ANTI_ALIAS_FLAG);
			isRunning = false;
			mSurface = mHolder.getSurface();
		}

		@Override
		public void run() {
			super.run();

			Canvas canvas = null;

			while (isRunning) {
				try {
					synchronized (mSurface) {
						canvas = mHolder.lockCanvas(null);
						if (canvas != null) {
							doDraw(canvas, paint);
						}
						if (mSurface.isValid() && canvas != null && !isDestroyed) {
							mHolder.unlockCanvasAndPost(canvas);
						}
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

			mSurface.release();
		}
	}

	public abstract void doDraw(Canvas canvas, Paint paint);

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		thread.isRunning = false;
		isDestroyed = true;
	}

	@Override
	public void onFinishTemporaryDetach() {
		super.onFinishTemporaryDetach();
	}
}
