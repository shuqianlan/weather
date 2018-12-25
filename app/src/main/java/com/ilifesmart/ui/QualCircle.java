package com.ilifesmart.ui;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.ilifesmart.model.Point;
import com.ilifesmart.utils.DensityUtils;
import com.ilifesmart.weather.R;

public class QualCircle extends View {
	public static final String TAG = "QualCircle";

	public interface ITranslateListener {
		void onTranslationStart();
		void onTranslation(int percent);
		void onTranslationCancel();
		void onTranslationStop();
		void onPresetGradientColor(boolean isNext, QualCircle qualCircle); // need startColor,endColor
	}

	private ITranslateListener iListener;
	private Paint mPathCirclePaint;
	private float mCenterX, mCenterY;
	private Path mQualCircle;
	private float mCircleParameter = 0.551915024494f;
	private float mRadius;
	private ValueAnimator mValueAnimator;
	private AnimatorSet mAnimatorSet;
	private float mCircleMaxOffsetParameter = 0.96f;
	private float mDampFactor = 0.36f;
	private float mRightMaxDistanceX;
	private float mRightMinDistanceX;
	private float mLeftMaxDistanceX;
	private float mLeftMinDistanceX;
	private float mAbsMaxOffsetX;
	private boolean currDropIn;
	private Point[] mPoints = new Point[12];

	private int mGradientStartColor;
	private int mGradientEndColor;
	private int mCurrColor;

	private float mDistanceOffsexX;

	public QualCircle(Context context) {
		this(context, null);
	}

	public QualCircle(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public QualCircle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initizlize(attrs);
	}

	private void initizlize(AttributeSet attrs) {
		mPathCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPathCirclePaint.setStyle(Paint.Style.FILL);

		mQualCircle = new Path();
		mDistanceOffsexX = DensityUtils.dp2px(getContext(), 120);

		mCurrColor = getResources().getColor(R.color.colorPrimary);
		mGradientEndColor = mGradientStartColor = mCurrColor;
		if (attrs != null) {
			TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.QualCircle);
			mGradientStartColor = a.getColor(R.styleable.QualCircle_GradientStartColor, mCurrColor);
			mGradientEndColor = a.getColor(R.styleable.QualCircle_GradientEndColor, mCurrColor);
			a.recycle();
		}
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		float contW = w - getPaddingLeft() - getPaddingRight();
		float contH = h - getPaddingTop() - getPaddingBottom();
		mRadius = Math.min(contH, contW)/2;
		mCenterX = getPaddingLeft() + contW/2;
		mCenterY = getPaddingTop() + contH/2;

		mRightMaxDistanceX = mCenterX + (mCircleMaxOffsetParameter+1)*mRadius;
		mRightMinDistanceX = mCenterX + mRadius;
		mLeftMaxDistanceX  = mCenterX - (mCircleMaxOffsetParameter+1)*mRadius;
		mLeftMinDistanceX  = mCenterX - mRadius;
		mAbsMaxOffsetX = mCircleMaxOffsetParameter * mRadius;

		mPoints[0] = new Point(mCenterX, mCenterY+mRadius);
		mPoints[1] = new Point(mCenterX+mRadius*mCircleParameter, mCenterY+mRadius);
		mPoints[2] = new Point(mCenterX+mRadius, mCenterY+(mRadius*mCircleParameter));
		mPoints[3] = new Point(mCenterX+mRadius, mCenterY);
		mPoints[4] = new Point(mCenterX+mRadius, mCenterY-(mRadius*mCircleParameter));
		mPoints[5] = new Point(mCenterX+mRadius*mCircleParameter, mCenterY-mRadius);
		mPoints[6] = new Point(mCenterX, mCenterY-mRadius);
		mPoints[7] = new Point(mCenterX-mRadius*mCircleParameter, mCenterY-mRadius);
		mPoints[8] = new Point(mCenterX-mRadius, (mCenterY-mRadius*mCircleParameter));
		mPoints[9] = new Point(mCenterX-mRadius, (mCenterY));
		mPoints[10] = new Point(mCenterX-mRadius, (mCenterY+mRadius*mCircleParameter));
		mPoints[11] = new Point(mCenterX-mRadius*mCircleParameter, (mCenterY+mRadius));
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		mPathCirclePaint.setColor(mCurrColor);
		mQualCircle.reset();
		mQualCircle.moveTo(mPoints[0].x, mPoints[0].y);
		for (int i = 1; i < 11; i+=3) {
			mQualCircle.cubicTo(mPoints[i].x, mPoints[i].y, mPoints[i+1].x, mPoints[i+1].y, mPoints[(i+2)%12].x, mPoints[(i+2)%12].y);
		}

		canvas.drawPath(mQualCircle, mPathCirclePaint);
	}

	private void startAnimation() {
		float offsetRightDistanceX = mPoints[2].x - mRightMinDistanceX;
		float offsetLeftDistanceX = mPoints[8].x - mLeftMinDistanceX;

		// 回归
		if (Math.max(Math.abs(offsetLeftDistanceX), Math.abs(offsetRightDistanceX)) < mAbsMaxOffsetX) {
			resetAnimation();
		} else {
			startTranslateAnimation();
		}
	}

	private void resetAnimation() {
		float offsetRightDistanceX = mPoints[2].x - mRightMinDistanceX;
		float offsetLeftDistanceX = mPoints[8].x - mLeftMinDistanceX;
		if (offsetLeftDistanceX <= 0.001 && offsetRightDistanceX <= 0.001) {
			mPoints[2].x = mPoints[3].x = mPoints[4].x = mRightMinDistanceX;
			mPoints[8].x = mPoints[9].x = mPoints[10].x = mLeftMinDistanceX;
			if (iListener != null) {
				iListener.onTranslationCancel();
			}
			return;
		}
		float targetDistance = (currDropIn) ? offsetRightDistanceX : offsetLeftDistanceX;
		mValueAnimator = ValueAnimator.ofFloat(targetDistance, 0);
		mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				if (currDropIn) {
					mPoints[2].x = mPoints[3].x = mPoints[4].x = mRightMinDistanceX + ((float)animation.getAnimatedValue());
				} else {
					mPoints[8].x = mPoints[9].x = mPoints[10].x = mLeftMinDistanceX + ((float)animation.getAnimatedValue());
				}
				invalidate();
			}
		});
		mValueAnimator.addListener(new Animator.AnimatorListener() {
			@Override
			public void onAnimationStart(Animator animation) {

			}

			@Override
			public void onAnimationEnd(Animator animation) {
				if (iListener != null) {
					iListener.onTranslationStop();
				}
			}

			@Override
			public void onAnimationCancel(Animator animation) {
				if (iListener != null) {
					iListener.onTranslationCancel();
				}
			}

			@Override
			public void onAnimationRepeat(Animator animation) {

			}
		});
		mValueAnimator.setDuration(500);
		mValueAnimator.setInterpolator(new LinearInterpolator());
		mValueAnimator.start();
	}

	private float getOvsershoot(float value) {
		//	http://inloop.github.io/interpolator/
		float tension = 2.0f;
		value -= 1.0f;
		return value * value * ((tension + 1) * value + tension) + 1.0f;
	}

	private void startTranslateAnimation() {
		if (iListener != null) {
			iListener.onPresetGradientColor(currDropIn, this);
		}
		ValueAnimator colorAnimation = ValueAnimator.ofArgb(mGradientStartColor, mGradientEndColor);
		ValueAnimator translationAnim = ValueAnimator.ofFloat(0, 13);
		mAnimatorSet = new AnimatorSet();
		mAnimatorSet.play(colorAnimation).with(translationAnim);
		translationAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			float distanceX = 0.3f*mRadius;
			float mTranslationX = getTranslationX();
			int index_1 = currDropIn ? 8 : 2;
			int index_2 = currDropIn ? 2 : 8;
			int num = currDropIn ? 1 : -1;
			float mMinX_1 = currDropIn ? mLeftMinDistanceX : mRightMinDistanceX;
			float mMinX_2 = currDropIn ? mRightMinDistanceX : mLeftMinDistanceX;

			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				if (animation.getAnimatedValue() != null) {
					float i = (float) animation.getAnimatedValue();

					if (i <= 4) {
						float tmp = i / 4 * mAbsMaxOffsetX * num;
						mPoints[index_1].x = mMinX_1 - tmp; // 8, 2
						mPoints[index_1 + 1].x = mMinX_1 - tmp;
						mPoints[index_1 + 2].x = mMinX_1 - tmp;
					} else if (i <= 8) {
						float tmp = (1 - (i - 4) / 4) * mAbsMaxOffsetX * num;
						mPoints[index_2].x = mMinX_2 + tmp;
						mPoints[index_2 + 1].x = mMinX_2 + tmp;
						mPoints[index_2 + 2].x = mMinX_2 + tmp;
					} else if (i <= 12) {
						float tmp2 = (i - 8) / 4;
						float tmp = (1 - tmp2) * mAbsMaxOffsetX * num - tmp2*distanceX*num;
						mPoints[index_1].x = mMinX_1 - tmp;
						mPoints[index_1 + 1].x = mMinX_1 - tmp;
						mPoints[index_1 + 2].x = mMinX_1 - tmp;
					} else if (i < 13) {
						float tmp = i - 12; // 0->1
						tmp = getOvsershoot(tmp);
						tmp = tmp*distanceX*num - distanceX*num;
						mPoints[index_1].x = mMinX_1 - tmp;
						mPoints[index_1 + 1].x = mMinX_1 - tmp;
						mPoints[index_1 + 2].x = mMinX_1 - tmp;
					} else {
						mPoints[index_1].x = mMinX_1;
						mPoints[index_1 + 1].x = mMinX_1;
						mPoints[index_1 + 2].x = mMinX_1;
					}

					if (i<=12) { // translationX
						setTranslationX(mTranslationX + i / 12 * getDistanceOffsexX() * (currDropIn ? 1 : -1));
					} else {
						setTranslationX(mTranslationX + getDistanceOffsexX() * (currDropIn ? 1 : -1));
					}
					if (iListener != null) {
						iListener.onTranslation((int)(animation.getAnimatedFraction()*100));
					}

					invalidate();
				}
			}
		});
		colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				mCurrColor = (int)animation.getAnimatedValue();
				invalidate();
			}
		});
		mAnimatorSet.addListener(new Animator.AnimatorListener() {
			@Override
			public void onAnimationStart(Animator animation) {
				if (iListener != null) {
					iListener.onTranslationStart();
				}
			}

			@Override
			public void onAnimationEnd(Animator animation) {
				if (iListener != null) {
					iListener.onTranslationStop();
				}
			}

			@Override
			public void onAnimationCancel(Animator animation) {
				if (iListener != null) {
					iListener.onTranslationCancel();
				}
			}

			@Override
			public void onAnimationRepeat(Animator animation) {

			}
		});
		mAnimatorSet.setDuration(1000);
		mAnimatorSet.start();
	}

	private void stopAnimation() {
		if (mValueAnimator != null && mValueAnimator.isRunning()) {
			mValueAnimator.cancel();
		}

		if (mAnimatorSet != null && mAnimatorSet.isRunning()) {
			mAnimatorSet.cancel();
		}
	}

	private boolean isAnimating() {
		return (mValueAnimator != null && mValueAnimator.isRunning()) || (mAnimatorSet != null && mAnimatorSet.isRunning());
	}

	private float lastTouchX;
	private float downX;

	@Override
	public synchronized boolean onTouchEvent(MotionEvent event) {
		if (isAnimating()) {
			return true;
		}
		float touchX = event.getX();
		switch (event.getActionMasked()) {
			case MotionEvent.ACTION_DOWN:
				downX = touchX;
				break;
			case MotionEvent.ACTION_MOVE:
				float offsetX = touchX - lastTouchX;
				float distanceX = touchX - downX;
				if (distanceX >= 0) {
					float newX = (mPoints[2].x+offsetX*mDampFactor);
					mPoints[2].x = ((mPoints[2].x > mRightMaxDistanceX) ? mRightMaxDistanceX : ((newX) < mRightMinDistanceX ? mRightMinDistanceX : newX));
					mPoints[3].x = ((mPoints[3].x > mRightMaxDistanceX) ? mRightMaxDistanceX : ((newX) < mRightMinDistanceX ? mRightMinDistanceX : newX));
					mPoints[4].x = ((mPoints[4].x > mRightMaxDistanceX) ? mRightMaxDistanceX : ((newX) < mRightMinDistanceX ? mRightMinDistanceX : newX));
				} else {
					float newX = (mPoints[8].x+offsetX*mDampFactor);
					mPoints[8].x  = ((mPoints[8].x < mLeftMaxDistanceX)  ? mLeftMaxDistanceX : ((newX) > mLeftMinDistanceX ? mLeftMinDistanceX : newX));
					mPoints[9].x  = ((mPoints[9].x < mLeftMaxDistanceX)  ? mLeftMaxDistanceX : ((newX) > mLeftMinDistanceX ? mLeftMinDistanceX : newX));
					mPoints[10].x = ((mPoints[10].x < mLeftMaxDistanceX) ? mLeftMaxDistanceX : ((newX) > mLeftMinDistanceX ? mLeftMinDistanceX : newX));
				}
				currDropIn = (distanceX >= 0);
				invalidate();
				break;
			case MotionEvent.ACTION_UP:
				startAnimation();
				break;
			default:
				return super.onTouchEvent(event);
		}

		lastTouchX = touchX;
		return true;
	}

	public float getDistanceOffsexX() {
		return mDistanceOffsexX;
	}

	public void setDistanceOffsexX(float distanceX) {
		mDistanceOffsexX = distanceX;
	}

	public QualCircle setGradientStartColor(int gradientStartColor) {
		mCurrColor = mGradientStartColor = gradientStartColor;
		return this;
	}

	public QualCircle setGradientEndColor(int gradientEndColor) {
		mGradientEndColor = gradientEndColor;
		return this;
	}

	public ITranslateListener getiTranslationListener() {
		return iListener;
	}

	public void setiTranslateListener(ITranslateListener iTranslationListener) {
		this.iListener = iTranslationListener;
	}

}
