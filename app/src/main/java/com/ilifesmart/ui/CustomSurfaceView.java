package com.ilifesmart.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
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

			while (true) {
				// 清屏代码，清所有缓冲画布的内容
				Rect dirty = new Rect(0, 0, 1, 1);
				Canvas canvas1 = mHolder.lockCanvas(dirty);

				if (getWidth() == canvas1.getClipBounds().width() && getHeight() == canvas1.getClipBounds().height()) {
					canvas1.drawColor(Color.BLACK);
					mHolder.unlockCanvasAndPost(canvas1);
				} else {
					mHolder.unlockCanvasAndPost(canvas1);
					break;
				}
			}

			while (isRunning) {
				try {
					synchronized (mSurface) {
						canvas = mHolder.lockCanvas(null);
						// 如果某缓冲屏幕未被画过，则其大小应为指定的rect的区域。当返回的区域大小和当前的已经不一致，则可以正式绘画.
						if (canvas != null) {
							canvas.drawColor(Color.TRANSPARENT);
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
