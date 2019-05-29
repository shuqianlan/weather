package com.ilifesmart.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class WarriorSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

	private Context mContext;
	private SurfaceHolder mHolder;
	private LoopThread thread;


	public WarriorSurfaceView(Context context) {
		this(context, null);
	}

	public WarriorSurfaceView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);

	}

	public WarriorSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		this.mContext = context;
		initialize(attrs);
	}

	private void initialize(AttributeSet attrs) {
		mHolder = getHolder();
		setZOrderOnTop(true);
		mHolder.addCallback(this);

		thread = new LoopThread(getContext(), mHolder);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		thread.isRunning = true;
		thread.start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		thread.isRunning = false;
		try {
			thread.join(); // 等待线程死去
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private class LoopThread extends Thread {
		SurfaceHolder mSurfaceHolder;
		Context mContext;
		boolean isRunning;
		float radius = 10f;
		Paint paint;

		public LoopThread(Context context, SurfaceHolder holder) {
			this.mContext = context;
			this.mSurfaceHolder = holder;
			paint = new Paint(Paint.ANTI_ALIAS_FLAG);
			isRunning = false;

			paint.setColor(Color.YELLOW);
			paint.setStyle(Paint.Style.STROKE);
		}

		@Override
		public void run() {
			super.run();

			Canvas canvas = null;

			while (isRunning) {
				try {
					synchronized (mSurfaceHolder) {
						canvas = mSurfaceHolder.lockCanvas(null); // surfaceview会保留之前的图形，此处清空之前的图形
						doDraw(canvas);
						Thread.sleep(50);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				} finally {
					mSurfaceHolder.unlockCanvasAndPost(canvas); // 释放锁
				}
			}
		}

		public void doDraw(Canvas canvas) {
			canvas.drawColor(Color.BLACK);
			canvas.translate(200, 200);
			canvas.drawCircle(0,0,radius++, paint);

			radius = (radius > 100) ? 10f : radius;
		}
	}

}
