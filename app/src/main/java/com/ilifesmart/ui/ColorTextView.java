package com.ilifesmart.ui;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import androidx.appcompat.widget.AppCompatTextView;
import android.util.AttributeSet;

public class ColorTextView extends AppCompatTextView {

	private LinearGradient mHorizonalShader;

	public ColorTextView(Context context) {
		this(context, null);
	}

	public ColorTextView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ColorTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	ValueAnimator animator;
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		int char_width = (int)getPaint().measureText(getText().toString());
		mHorizonalShader = new LinearGradient(-char_width, 0, 0, 0, new int[] {
						getCurrentTextColor(), 0xff00ff00, getCurrentTextColor()
		}, new float[] {0, 0.5f, 1}, Shader.TileMode.CLAMP);

		if (animator != null) {
			animator.cancel();
		}
		animator = new ValueAnimator().ofInt(0, char_width * 2);
		animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				mDx = (int) animation.getAnimatedValue();
				postInvalidate();
			}
		});
		animator.setDuration(4000);
		animator.setRepeatCount(ValueAnimator.INFINITE);
		animator.setRepeatMode(ValueAnimator.RESTART);
		animator.start();
	}

	private int mDx = 0;
	@Override
	protected void onDraw(Canvas canvas) {
		Paint mPaint = getPaint();
		Matrix matrix = new Matrix();
		matrix.setTranslate(mDx, 0);
		mHorizonalShader.setLocalMatrix(matrix);
		mPaint.setShader(mHorizonalShader);

		// 类似RecycylerView中的过场加载动画.
		super.onDraw(canvas);
	}
}
