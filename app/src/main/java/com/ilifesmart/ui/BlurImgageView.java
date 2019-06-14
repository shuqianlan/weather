package com.ilifesmart.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.ilifesmart.weather.R;

public class BlurImgageView extends View {

	private Paint mPaint;
	private Bitmap mBitmap;
	private Bitmap mDstBitmap;
	private Matrix mAlphaMatrix;
	private Matrix mSrcMatrix;
	private Matrix mMatrix;
	private BlurMaskFilter mBlurMaskFilter;

	private static final int RADIUS_BLUR_SHADER = 20;
	private BlurMaskFilter.Blur mode = BlurMaskFilter.Blur.INNER;

	public BlurImgageView(Context context) {
		this(context, null);
	}

	public BlurImgageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public BlurImgageView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		setLayerType(LAYER_TYPE_SOFTWARE, null);
		mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.spot);
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mBlurMaskFilter = new BlurMaskFilter(4, mode);
		mPaint.setMaskFilter(mBlurMaskFilter);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		Log.d("Surface", "onSizeChanged: [w,h]: [" + w + "," + h + "]");
		Log.d("Surface", "onSizeChanged: [bw,bh]: [" + mBitmap.getWidth() + "," + mBitmap.getHeight() + "]");

		mAlphaMatrix = new Matrix();
		mSrcMatrix = new Matrix();
		mMatrix = new Matrix();
		float scale = Math.min((float) h/mBitmap.getHeight(), (float) w/mBitmap.getWidth());
		Log.d("scale", "onSizeChanged: scale " + scale);

		mDstBitmap = mBitmap.extractAlpha();
		float targetWidth = mBitmap.getWidth()*scale;
		float targetHeight = mBitmap.getHeight()*scale;

		mAlphaMatrix.postScale(scale, scale);
		mAlphaMatrix.postTranslate(w*3/4-targetWidth/2, h/2-targetHeight/2);

		mSrcMatrix.postScale(scale, scale);
		mSrcMatrix.postTranslate(w/4-targetWidth/2, h/2-targetHeight/2);

		mMatrix.postScale(scale, scale);
		mMatrix.postTranslate(w/2-targetWidth/2, h/2-targetHeight/2);
		mPaint.setMaskFilter(mBlurMaskFilter);
	}

	@Override
	protected void onDraw(Canvas canvas) {

		//	原图(MaskFilter)
		canvas.drawBitmap(mBitmap, mMatrix, mPaint);

		//  Alpha(MaskFilter)
		canvas.drawBitmap(mDstBitmap, mAlphaMatrix, mPaint);

		//	覆盖图
		canvas.drawBitmap(mBitmap, mAlphaMatrix, null);
		//  原图
		canvas.drawBitmap(mBitmap, mSrcMatrix, null);
	}

	/*
	* Blur.NORMAL: Blur inside and outside the original border.  (内外模糊)
	* Blur.SOLID:  Draw solid inside the border, blur outside.   (以边界色模糊外边界)
	* Blur.OUTER:  Draw nothing inside the border, blur outside. (外边界模糊)
	* Blur.INNER:  Blur inside the border, draw nothing outside. (内边界模糊)
	* */
	public void setBlurMode(BlurMaskFilter.Blur mode) {
		mBlurMaskFilter = new BlurMaskFilter(RADIUS_BLUR_SHADER, mode);
		mPaint.setMaskFilter(mBlurMaskFilter);
		postInvalidate();
	}

}
