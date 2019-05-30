package com.ilifesmart.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public abstract class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {

	protected Context mContext;
	private SurfaceHolder mHolder;
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

		thread = new LoopThread(context, mHolder);
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
		thread.isRunning = false;
		try {
			thread.join();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		onSurfaceDestroyed(holder);
	}

	public class LoopThread extends Thread {
		SurfaceHolder mSurfaceHolder;
		Context mContext;
		boolean isRunning;
		Paint paint;

		public LoopThread(Context context, SurfaceHolder holder) {
			this.mContext = context;
			this.mSurfaceHolder = holder;
			paint = new Paint(Paint.ANTI_ALIAS_FLAG);
			isRunning = false;
		}

		@Override
		public void run() {
			super.run();

			Canvas canvas = null;

			while (isRunning) {
				try {
					synchronized (mSurfaceHolder) {
						canvas = mSurfaceHolder.lockCanvas(null); // surfaceview会保留之前的图形，此处清空之前的图形
						doDraw(canvas, paint);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				} finally {
					mSurfaceHolder.unlockCanvasAndPost(canvas); // canvas生效
				}
			}
		}

	}

	public abstract void doDraw(Canvas canvas, Paint paint);

	public void onSurfaceCreated(SurfaceHolder holder) {}

	public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

	public void onSurfaceDestroyed(SurfaceHolder holder) {}

}
