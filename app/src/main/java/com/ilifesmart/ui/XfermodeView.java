package com.ilifesmart.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

import com.ilifesmart.utils.ImageResizer;

public class XfermodeView extends View {

	private Paint mDstPaint;
	private Paint mSrcPaint;
	private PorterDuff.Mode mode = PorterDuff.Mode.SRC_IN;

	public XfermodeView(Context context) {
		this(context, null);
	}

	public XfermodeView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public XfermodeView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		mDstPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mDstPaint.setStyle(Paint.Style.FILL);

		mSrcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mSrcPaint.setStyle(Paint.Style.FILL);

	}

	private Bitmap mDstBitmap;
	private Bitmap mSrcBitmap;

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		mDstBitmap = ImageResizer.creatBitmap(h, h, Color.RED, 0);
		mSrcBitmap = ImageResizer.creatBitmap(h, h, Color.BLUE, 1);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		int layerId = canvas.saveLayer(0, 0, getWidth(), getHeight(), null);
		canvas.drawBitmap(mDstBitmap, getWidth()/2-getHeight()/2, 0, mDstPaint);

		mSrcPaint.setXfermode(new PorterDuffXfermode(mode));
		canvas.drawBitmap(mSrcBitmap, getWidth()/2-getHeight()/2, 0, mSrcPaint);

		canvas.drawBitmap(mDstBitmap, getHeight()/2, 0, mDstPaint);
		canvas.drawBitmap(mSrcBitmap, getHeight(), 0, mSrcPaint);

		mSrcPaint.setXfermode(null);

		canvas.restoreToCount(layerId);
	}

	public void setXfermode(PorterDuff.Mode mode) {
		this.mode = mode;
		postInvalidate();
	}
}
