package com.ilifesmart.ui;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.ilifesmart.utils.DensityUtils;

public class BlurRect extends View {
	private Paint mPaint;
	private TextPaint mTextPaint;

	public BlurRect(Context context) {
		this(context, null);
	}

	public BlurRect(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public BlurRect(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setColor(Color.CYAN);

		mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG|TEXT_ALIGNMENT_CENTER);
		mTextPaint.setColor(Color.WHITE);
		mTextPaint.setTextSize(DensityUtils.sp2px(context, 22));
		setLayerType(LAYER_TYPE_SOFTWARE, null);
	}

	private int rectWidth = 0;
	private int GAP = 10;
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		rectWidth = (w-GAP*(4+1))/4;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		mPaint.setMaskFilter(new BlurMaskFilter(6, BlurMaskFilter.Blur.NORMAL));
		canvas.drawRect(GAP, 0, GAP+rectWidth, getHeight(), mPaint);

		mPaint.setMaskFilter(new BlurMaskFilter(6, BlurMaskFilter.Blur.SOLID));
		canvas.drawRect(2*GAP+rectWidth, 0, 2*GAP+2*rectWidth, getHeight(), mPaint);

		mPaint.setMaskFilter(new BlurMaskFilter(6, BlurMaskFilter.Blur.INNER));
		canvas.drawRect(3*GAP+rectWidth*2, 0, 3*GAP+3*rectWidth, getHeight(), mPaint);

		mPaint.setMaskFilter(new BlurMaskFilter(6, BlurMaskFilter.Blur.OUTER));
		canvas.drawRect(4*GAP+3*rectWidth, 0, 4*GAP+4*rectWidth, getHeight(), mPaint);

		setRectText(1, "Normal", canvas);
		setRectText(2, "Solid", canvas);
		setRectText(3, "Inner", canvas);
		setRectText(4, "Outer", canvas);
	}

	private void setRectText(int index, String text, Canvas canvas) {
		Rect rect = new Rect();
		float textWidth = mTextPaint.measureText(text);
		Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
		float offset = (fontMetrics.descent+fontMetrics.ascent)/2;
		canvas.drawText(text, (index)*GAP+rectWidth*(index*2-1)/2-textWidth/2, getHeight()/2-offset, mTextPaint);
	}

}
