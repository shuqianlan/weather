package com.ilifesmart.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.ilifesmart.model.BarrageText;
import com.ilifesmart.utils.DensityUtils;

public class BarrageView extends View {

	private Paint mTextPaint;
	private int mWidth, mHeight;
	private boolean isRunning;
	BarrageText st1;

	public BarrageView(Context context) {
		this(context,null);
	}

	public BarrageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public BarrageView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		initialize();
	}

	private void initialize() {
		mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mTextPaint.setColor(Color.parseColor("#FFEEAa"));
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);

		mWidth = right - left;
		mHeight = bottom - top;

		int y = (int)(getPaddingBottom() + (Math.random()*mHeight));
		st1 = new BarrageText().setText("AAAAAAA").setSpeed(5).setTextColor(Color.RED).setTextSize(DensityUtils.sp2px(getContext(), 12)).setX(getWidth()+step).setY(y);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	private int step = 5;
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		if (isRunning) {
			canvas.drawText(st1.getText(), st1.getX(), st1.getY(), mTextPaint);
			invalidate();
		}

	}

	public void startRunning() {
		isRunning = true;
		invalidate();
	}

	public void stopRunning() {
		isRunning = false;
	}
}
