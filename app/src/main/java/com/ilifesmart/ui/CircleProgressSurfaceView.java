package com.ilifesmart.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;

import com.ilifesmart.utils.DensityUtils;
import com.ilifesmart.weather.R;

import java.util.concurrent.atomic.AtomicInteger;

public class CircleProgressSurfaceView extends CustomSurfaceView {

	private Path mCirclePath;
	private Path mCircle2Path;

	private Path mDstPath;
	private Path mDst2Path;

	private int  mPivotX;
	private int  mPivotY;
	private float mRadius;

	private PathMeasure mPathMeasure;
	private PathMeasure mPath2Measure;

	private Bitmap mArrow;

	public CircleProgressSurfaceView(Context context) {
		this(context, null);
	}

	public CircleProgressSurfaceView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CircleProgressSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		initialize();
	}

	private void initialize() {
		mCirclePath = new Path();
		mDstPath = new Path();
		mDst2Path = new Path();
		mCircle2Path = new Path();

		mArrow = BitmapFactory.decodeResource(getResources(), R.drawable.arrow);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		mRadius = Math.min(w, h)/2.0f;
		int contentWidth  = w - getPaddingLeft() - getPaddingRight();
		int contentHeight = h - getPaddingTop() - getPaddingBottom();
		mPivotX = getPaddingLeft() + contentWidth/2;
		mPivotY = getPaddingTop() + contentHeight/2;

		mCirclePath.addCircle(mPivotX, mPivotY, mRadius, Path.Direction.CW);
		mCircle2Path.addCircle(mPivotX, mPivotY, mRadius*0.9f, Path.Direction.CW);

		mPathMeasure = new PathMeasure(mCirclePath, true);
		length = (int) mPathMeasure.getLength();

		mPath2Measure = new PathMeasure(mCircle2Path, true);
		length2 = (int) mPath2Measure.getLength();

	}

	private AtomicInteger delay = new AtomicInteger(0);
	private int length;
	private int length2;
	private float progress = delay.get();

	@Override
	public void doDraw(Canvas canvas, Paint paint) {
		canvas.drawColor(Color.WHITE);
		paint.setColor(Color.GRAY);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(DensityUtils.dp2px(getContext(), 1));

		int targetLength = (int) (length * progress);
		int	beginLength = (progress <= 0.5f) ? 0 :(int)((progress-0.5)*length*2);
		mDstPath.reset();
		mPathMeasure.getSegment(beginLength, targetLength, mDstPath, true);
		canvas.drawPath(mDstPath, paint);

		canvas.save();
		canvas.rotate(180, mPivotX, mPivotY);
		mDst2Path.reset();
		int target2Length = (int) (length2 * progress);
		int begin2Length = ((progress <= 0.5f) ? 0 :(int)((progress-0.5)*length2*2));
		mPath2Measure.getSegment(begin2Length, target2Length, mDst2Path, true);
		canvas.drawPath(mDst2Path, paint);
		canvas.restore();

		// 图片旋转
		Matrix matrix = new Matrix();
		float[] pos = new float[2];
		float[] tan = new float[2];
		mPathMeasure.getPosTan(targetLength, pos, tan);
		float degree = (float) (Math.atan2(tan[1], tan[0])*180.0/Math.PI);
		matrix.postRotate(degree, mArrow.getWidth()/2, mArrow.getHeight()/2);
		matrix.postTranslate(mPivotX-mArrow.getWidth()/2, mPivotY-mArrow.getHeight()/2); // 中心点
		canvas.drawBitmap(mArrow, matrix, paint);

		try {
			Thread.sleep(16);
			progress = (delay.incrementAndGet()%100)/100.0f;
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}
