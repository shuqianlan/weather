package com.ilifesmart.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

public class CustomDrawable extends Drawable {

	private Paint mPaint;
	private Bitmap mDstBitmap;
	private BitmapShader mShader;
	private RectF mBound;

	public CustomDrawable(Bitmap bitmap) {
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mDstBitmap = bitmap;
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.drawRoundRect(mBound, 20, 20, mPaint);
	}

	@Override
	public void setAlpha(int alpha) {
		mPaint.setAlpha(alpha);
	}

	@Override
	public void setColorFilter(ColorFilter colorFilter) {
		mPaint.setColorFilter(colorFilter);
	}

	/*
	* PixelFormat.UNKNOWN      : 未知
	* PixelFormat.TRANSLUCENT  : 含alpha通道
	* PixelFormat.TRANSPARENT  : 透明通道
	* PixelFormat.OPAQUE       ：完全没有alpha，会覆盖掉底图
	*
	* */
	@Override
	public int getOpacity() {
		return PixelFormat.TRANSLUCENT;
	}

	/* 设定边界 */
	@Override
	public void setBounds(int left, int top, int right, int bottom) {
		super.setBounds(left, top, right, bottom);
		mDstBitmap = Bitmap.createScaledBitmap(mDstBitmap, right-left, bottom-top, true);
		mShader = new BitmapShader(mDstBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
		mPaint.setShader(mShader);

		mBound = new RectF(left, top, right, bottom);
	}

	@Override
	public int getIntrinsicHeight() {
		return mDstBitmap.getHeight();
	}

	@Override
	public int getIntrinsicWidth() {
		return mDstBitmap.getWidth();
	}

}
