package com.spannableText;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.style.ReplacementSpan;

public class RoundRadiusTagSpan extends ReplacementSpan {

	public static final String TAG = "RoundRadiusTagSpan";
	private int mRadius;
	private int bgColor;
	private int textColor;
	private int mSize;

	public RoundRadiusTagSpan() {
		this.mRadius = 10;
		this.bgColor = Color.RED;
		this.textColor = Color.GRAY;
	}

	@Override
	public int getSize( Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
		mSize = (int)(paint.measureText(text, start, end) + mRadius*2);
		return mSize + 5;
	}


	/*
	* y:baseline
	* top: top
	* bottom: bottom
	* */
	@Override
	public void draw( Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom,  Paint paint) {
		int defaultColor = paint.getColor();//保存文字颜色
		float defaultStrokeWidth = paint.getStrokeWidth();

		//绘制圆角矩形
		paint.setColor(bgColor);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(5);
		paint.setAntiAlias(true);
		RectF rectF = new RectF(x, y - 2.5f - mRadius + paint.ascent(), x + mSize + 2.5f, y + paint.descent() + mRadius + 2.5f);
		//设置文字背景矩形，x为span其实左上角相对整个TextView的x值，y为span左上角相对整个View的y值。
		// paint.ascent()获得文字上边缘，paint.descent()获得文字下边缘
		//x+2.5f解决线条粗细不一致问题
		canvas.drawRoundRect(rectF, mRadius, mRadius, paint);

		//绘制文字
		paint.setColor(textColor);
		paint.setStyle(Paint.Style.FILL);
		paint.setStrokeWidth(defaultStrokeWidth);
		canvas.drawText(text, start, end, x + mRadius, y, paint);//此处mRadius为文字右移距离

		paint.setColor(defaultColor);//恢复画笔的文字颜色
	}

}
