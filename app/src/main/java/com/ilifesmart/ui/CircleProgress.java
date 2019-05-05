package com.ilifesmart.ui;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.ilifesmart.utils.DensityUtils;

public class CircleProgress extends View {

	private Paint mOutCirclePaint;
	private Paint mBiasBottomPaint;
	private Paint mBiasTopPaint;
	private Paint mNumberPaint;
	private Paint mUnitPaint;
	private Paint mTmpPaint;

	private int mWidth;
	private int mHeight;
	private int mRadius;
	private int mInnerRadius;
	private int mCenterX;
	private int mCenterY;
	private int mOffsetDistance;

	private String mNumContent = "60";
	private String mUnit = "℃";

	private LinearGradient mLinearGradient;
	private LinearGradient mTextGradient;

	public CircleProgress(Context context) {
		this(context, null);
	}

	public CircleProgress(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CircleProgress(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		initPaints();
	}

	private void initPaints() {
		mOutCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mOutCirclePaint.setColor(Color.LTGRAY);

		mTmpPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mTmpPaint.setColor(Color.WHITE);

		mBiasBottomPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mBiasBottomPaint.setStrokeWidth(DensityUtils.dp2px(getContext(), 2));
		mBiasBottomPaint.setColor(Color.LTGRAY);

		mBiasTopPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mBiasTopPaint.setColor(Color.LTGRAY);

		mNumberPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mNumberPaint.setTextSize(DensityUtils.sp2px(getContext(), 60));
		mNumberPaint.setTextAlign(Paint.Align.CENTER);
		mNumberPaint.setColor(Color.DKGRAY);

		mUnitPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mUnitPaint.setTextSize(DensityUtils.sp2px(getContext(), 30));
		mUnitPaint.setTextAlign(Paint.Align.LEFT);
		mUnitPaint.setColor(Color.DKGRAY);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		mWidth = w;
		mHeight = h;
		int width = mWidth - getPaddingLeft() - getPaddingRight();
		int height = mHeight - getPaddingTop() - getPaddingBottom();

		mRadius = (int)(Math.min(width, height)/2 * 0.6);
		mInnerRadius = (int)(Math.min(width, height)/2 * 0.4);
		mCenterX = getPaddingLeft() + width/2;
		mCenterY = getPaddingTop() + height/2;

		mOffsetDistance = mRadius - mInnerRadius - 10;
		mLinearGradient = new LinearGradient(mCenterX+mInnerRadius+5, mCenterY, mCenterX+mInnerRadius+5+mOffsetDistance*0.7f, mCenterY-mOffsetDistance*0.7f, Color.LTGRAY, Color.BLUE, Shader.TileMode.CLAMP);
		mTextGradient = new LinearGradient(mCenterX, mCenterY-mInnerRadius*0.4f, mCenterX, mCenterY+mInnerRadius*0.4f, Color.RED, Color.TRANSPARENT, Shader.TileMode.CLAMP);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		setLayerType(LAYER_TYPE_SOFTWARE, null);

		drawCircle(canvas);
		drawBiasLines(canvas);

		drawTextInnerCircle(canvas);
	}

	private void drawCircle(Canvas canvas) {
		mOutCirclePaint.setMaskFilter(new BlurMaskFilter(mRadius, BlurMaskFilter.Blur.SOLID));
		canvas.drawCircle(mCenterX, mCenterY, mRadius, mOutCirclePaint);
		mOutCirclePaint.setMaskFilter(new BlurMaskFilter(mRadius, BlurMaskFilter.Blur.NORMAL));
		canvas.drawCircle(mCenterX, mCenterY, mRadius- DensityUtils.dp2px(getContext(), 2), mTmpPaint);
	}

	private void drawBiasLines(Canvas canvas) {
		canvas.save();

		mBiasBottomPaint.setShader(mLinearGradient);
		for (int i = 0; i < 40; i++) {
			canvas.drawLine(mCenterX+mInnerRadius, mCenterY, mCenterX+mInnerRadius+mOffsetDistance*0.7f, mCenterY-mOffsetDistance*0.7f, mBiasBottomPaint);
			canvas.rotate(9, mCenterX, mCenterY);
		}
		canvas.restore();
		canvas.drawCircle(mCenterX, mCenterY, mInnerRadius, mBiasTopPaint);
	}

	public static final String TAG = "Circle";
	private void drawTextInnerCircle(Canvas canvas) {
		String number = "60";
		String unit = "℃";

//		canvas.drawLine(mCenterX-mInnerRadius, mCenterY, mCenterX+mInnerRadius, mCenterY, mTmpPaint);
		Paint.FontMetrics metrics = mNumberPaint.getFontMetrics();

		// top
//
//		mTmpPaint.setColor(Color.RED);
//		canvas.drawLine(mCenterX-mInnerRadius, mCenterY+metrics.top, mCenterX+mInnerRadius, mCenterY+metrics.top, mTmpPaint);
//		canvas.drawText("top", mCenterX, mCenterY+metrics.top, mTmpPaint);
//
		// ascent
//		mTmpPaint.setColor(Color.GREEN);
//		canvas.drawLine(mCenterX-mInnerRadius, mCenterY+metrics.ascent, mCenterX+mInnerRadius, mCenterY+metrics.ascent, mTmpPaint);
//		canvas.drawText("ascent", mCenterX, mCenterY+metrics.ascent, mTmpPaint);
//
		// base-line
//		mTmpPaint.setColor(Color.BLUE);
//		canvas.drawLine(mCenterX-mInnerRadius, mCenterY, mCenterX+mInnerRadius, mCenterY, mTmpPaint);
//		canvas.drawText("baseLine", mCenterX, mCenterY, mTmpPaint);
//
		// descent
//		mTmpPaint.setColor(Color.WHITE);
//		canvas.drawLine(mCenterX-mInnerRadius, mCenterY+metrics.descent, mCenterX+mInnerRadius, mCenterY+metrics.descent, mTmpPaint);
//		canvas.drawText("descentLine", mCenterX, mCenterY+metrics.descent, mTmpPaint);
//
		// bottom
//		mTmpPaint.setColor(Color.DKGRAY);
//		canvas.drawLine(mCenterX-mInnerRadius, mCenterY+metrics.bottom, mCenterX+mInnerRadius, mCenterY+metrics.bottom, mTmpPaint);
//		canvas.drawText("bottomLine", mCenterX, mCenterY+metrics.bottom, mTmpPaint);

		int textWidth = (int)mNumberPaint.measureText(number);
		int top = Math.abs((int)(0 - metrics.top));
		int bottom = Math.abs((int)(metrics.bottom));
		// text
		int baseLineY = mCenterY+Math.abs((top-bottom)/2);
		mNumberPaint.setShader(mTextGradient);
		canvas.drawText(number, mCenterX, baseLineY, mNumberPaint);
		mUnitPaint.setShader(mTextGradient);
		canvas.drawText(unit, mCenterX + textWidth/2 + 10, baseLineY, mUnitPaint);
	}
}
