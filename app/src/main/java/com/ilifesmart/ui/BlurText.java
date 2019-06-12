package com.ilifesmart.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;

import com.ilifesmart.weather.R;

public class BlurText extends CustomSurfaceView {
	private BlurMaskFilter mMaskFilter;
	private int  mPivotX;
	private int  mPivotX2;
	private int  mPivotY;
	private float mRadius;

	private Bitmap mTelephone;
	private BitmapShader shader;

	public BlurText(Context context) {
		this(context, null);
	}

	public BlurText(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public BlurText(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		mMaskFilter = new BlurMaskFilter(6, BlurMaskFilter.Blur.INNER);
		mTelephone  = BitmapFactory.decodeResource(getResources(), R.drawable.telephone);
		shader = new BitmapShader(mTelephone, Shader.TileMode.MIRROR, Shader.TileMode.MIRROR);
//		mTelephone.extractAlpha();
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		int contentWidth  = w - getPaddingLeft() - getPaddingRight();
		int contentHeight = h - getPaddingTop() - getPaddingBottom();

		mRadius = Math.min(contentWidth, contentHeight)/2.0f;
		mPivotX = getPaddingLeft() + contentWidth/4;
		mPivotX2 = getPaddingLeft() + contentWidth*3/4;
		mPivotY = getPaddingTop() + contentHeight/2;

	}

	@Override
	public void doDraw(Canvas canvas, Paint paint) {
		canvas.drawColor(Color.LTGRAY);
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		paint.setColor(Color.YELLOW);

//		canvas.save();
//		Matrix matrix = new Matrix();
//		float scale = mTelephone.getWidth()/(float)getWidth();
//		matrix.setScale(scale, scale);
//		shader.setLocalMatrix(matrix);
		paint.setShader(shader);
		canvas.drawCircle(mPivotX, mPivotY, mTelephone.getWidth()/2, paint);

//		canvas.restore();

//		canvas.save();
//		paint.setMaskFilter(mMaskFilter);
//		canvas.drawCircle(mPivotX, mPivotY, mRadius, paint);
//		canvas.restore();

		try {
			Thread.sleep(100);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void setMaskFilter(BlurMaskFilter.Blur type) {
		mMaskFilter = new BlurMaskFilter(+6, type);
	}
}
