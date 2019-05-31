package com.ilifesmart.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public abstract class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {

	protected Context mContext;
	protected SurfaceHolder mHolder;
	private MySurfaceView.LoopThread thread;

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

		thread = new LoopThread();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		thread.isRunning = true;
		thread.start();
		onSurfaceCreated(holder);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		onSurfaceChanged(holder, format, width, height);
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		mHolder.removeCallback(this);
		thread.isRunning = false;
		try {
			thread.join();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		onSurfaceDestroyed(holder);
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
						if (mHolder.getSurface().isValid()) {
							mHolder.unlockCanvasAndPost(canvas);
						}
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}

	}

	public abstract void doDraw(Canvas canvas, Paint paint);

	public void onSurfaceCreated(SurfaceHolder holder) {}

	public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

	public void onSurfaceDestroyed(SurfaceHolder holder) {}

}
