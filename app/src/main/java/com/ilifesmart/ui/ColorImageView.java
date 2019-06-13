package com.ilifesmart.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class ColorImageView extends View {

	private RadialGradient mRadialRepeatGradient;
	private RadialGradient mRadialClampGradient;
	private RadialGradient mRadialMirrorGradient;
	private Paint mPaint;

	private int pivotX;
	private int pivotX2;
	private int pivotX3;
	private int pivotY;
	private int mRadius;

	public ColorImageView(Context context) {
		this(context, null);
	}

	public ColorImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ColorImageView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		initialize();
	}

	private void initialize() {
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setStyle(Paint.Style.FILL);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		int width = w - getPaddingLeft() - getPaddingRight();
		int height = h - getPaddingTop() - getPaddingBottom();
		mRadius = Math.min(width, height) / 2;
		pivotX = w / 4;
		pivotX2 = w / 2;
		pivotX3 = w * 3 / 4;
		pivotY = getPaddingTop() + height / 2;

		mRadialRepeatGradient = new RadialGradient(pivotX, pivotY, mRadius/2, Color.RED, Color.GREEN, Shader.TileMode.REPEAT);
		mRadialClampGradient  = new RadialGradient(pivotX2, pivotY, mRadius/2, Color.RED, Color.GREEN, Shader.TileMode.CLAMP);
		mRadialMirrorGradient = new RadialGradient(pivotX3, pivotY, mRadius/2, Color.RED, Color.GREEN, Shader.TileMode.MIRROR);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		Log.d("ColorImageView", "onDraw: layer 1 " + canvas.getSaveCount());
		mPaint.setShader(mRadialRepeatGradient);
		canvas.drawCircle(pivotX, pivotY, mRadius, mPaint);

		Log.d("ColorImageView", "onDraw: layer 2 " + canvas.getSaveCount());
		mPaint.setShader(mRadialClampGradient);
		canvas.drawCircle(pivotX2, pivotY, mRadius, mPaint);

		Log.d("ColorImageView", "onDraw: layer 3 " + canvas.getSaveCount());
		mPaint.setShader(mRadialMirrorGradient);
		canvas.drawCircle(pivotX3, pivotY, mRadius, mPaint);

		int layId = canvas.saveLayer(0, 0, getWidth(), getHeight(), null);
		Log.d("ColorImageView", "onDraw: layerCount " + layId);
		Log.d("ColorImageView", "onDraw: layer 4 " + canvas.getSaveCount());
		canvas.restoreToCount(layId);
		Log.d("ColorImageView", "onDraw: layer " + canvas.getSaveCount());

		canvas.save();
		canvas.restore();
	}

}
