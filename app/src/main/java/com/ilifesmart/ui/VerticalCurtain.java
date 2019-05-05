package com.ilifesmart.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

public class VerticalCurtain extends View {

	public static final String TAG = "VerticalCurtain";

	private Paint mBackgroundPaint;
	private Paint mLinePaint;

	private double mLineWidthAspet = 0.77;
	private double mTopPadding = 50;
	private double mBottomPadding = 40;
	private int mArcRound = 30;

	private int progress = 0;
	private Path mBackgroundPath;

	public VerticalCurtain(Context context) {
		this(context, null);
	}

	public VerticalCurtain(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public VerticalCurtain(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mLinePaint.setStrokeWidth(1);
		mLinePaint.setColor(Color.parseColor("#979797"));

		mBackgroundPaint.setShader(new LinearGradient(0,0,getHeight(), 0, Color.parseColor("#646464"), Color.parseColor("#393939"), Shader.TileMode.CLAMP));
		mBackgroundPath = new Path();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		int width = getWidth() - (getPaddingLeft()+getPaddingRight());
		int height = (int)(getHeight() - (getPaddingBottom()+getPaddingTop()) - mArcRound*2 - mTopPadding - mBottomPadding); // 纯内容区高度

		int centerX = getPaddingLeft() + width/2;
		float halfLineWidth = (float) (getWidth() * mLineWidthAspet / 2);

		int startY = (int)(getPaddingTop() + mArcRound + mTopPadding);
		float lineGap = (float) Math.max(12.8, height/21);
		int percentStep = height/101;
		int bottomMaxY = (int)(startY + percentStep*progress + mArcRound + mBottomPadding);

		// 绘制中间矩形区
		canvas.drawRect(0, mArcRound+getPaddingTop(), getWidth(), bottomMaxY-mArcRound, mBackgroundPaint);

		// 绘制水平横线
		for (int i = 0; i < 20; i++) {
			startY += lineGap;
			if (bottomMaxY < startY) {
				break;
			}
			canvas.drawLine(centerX-halfLineWidth, startY, centerX+halfLineWidth, startY, mLinePaint);
		}

		// 绘制顶部圆角宽区域
		mBackgroundPath.reset();
		mBackgroundPath.moveTo(0, getPaddingTop()+mArcRound);
		mBackgroundPath.cubicTo(0, getPaddingTop()+mArcRound, 0, 0, mArcRound, getPaddingTop());
		mBackgroundPath.lineTo(getWidth()-mArcRound, getPaddingTop());
		mBackgroundPath.cubicTo(getWidth()-mArcRound, getPaddingTop(), getWidth(), getPaddingTop(), getWidth(), getPaddingTop()+mArcRound);
		mBackgroundPath.close();
		canvas.drawPath(mBackgroundPath, mBackgroundPaint);

		// 绘制底部圆角宽区域
		mBackgroundPath.reset();
		mBackgroundPath.moveTo(0, bottomMaxY-mArcRound);
		mBackgroundPath.cubicTo(0, bottomMaxY-mArcRound, 0, bottomMaxY, mArcRound, bottomMaxY);
		mBackgroundPath.lineTo(getWidth()-mArcRound, bottomMaxY);
		mBackgroundPath.cubicTo(getWidth()-mArcRound, bottomMaxY, getWidth(), bottomMaxY, getWidth(), bottomMaxY-mArcRound);
		mBackgroundPath.close();
		canvas.drawPath(mBackgroundPath, mBackgroundPaint);
	}

	public void setProgress(int progress) {
		this.progress = progress;
		invalidate();
	}
}
