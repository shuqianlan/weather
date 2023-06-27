package com.ilifesmart.path;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.ilifesmart.ui.QualCircle;
import com.ilifesmart.utils.DensityUtils;
import com.ilifesmart.weather.R;

import java.util.concurrent.atomic.AtomicInteger;

public class CircleActivity extends AppCompatActivity {

	public static final String TAG = "CircleActivity";
	QualCircle mQualCircle;
	TextView mBtn1;
	TextView mBtn2;
	TextView mBtn3;
	private AtomicInteger mCurrentIndex = new AtomicInteger(1);
	ValueAnimator mValueAnimator;
	private boolean once = true;

	private Point[] mLinearRightGradientColors = new Point[3];
	private Point[] mLinearLeftGradientColors = new Point[3];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_circle);
		mQualCircle = findViewById(R.id.qual_circle);
		mBtn1 = findViewById(R.id.btn1);
		mBtn2 = findViewById(R.id.btn2);
		mBtn3 = findViewById(R.id.btn3);


		mBtn2.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				mQualCircle.setDistanceOffsexX(mBtn2.getLeft()-mBtn1.getRight()+mBtn2.getWidth());
			}
		});

		mLinearRightGradientColors[0] = new Point(Color.RED, Color.GREEN);
		mLinearRightGradientColors[1] = new Point(Color.GREEN, Color.BLUE);
		mLinearRightGradientColors[2] = new Point(Color.GREEN, Color.BLUE);

		mLinearLeftGradientColors[0] = new Point(Color.GREEN, Color.RED);
		mLinearLeftGradientColors[1] = new Point(Color.RED, Color.GREEN);
		mLinearLeftGradientColors[2] = new Point(Color.GREEN, Color.BLUE);

		mQualCircle.setDistanceOffsexX(DensityUtils.dp2px(this, 120));
		mQualCircle.setGradientStartColor(mLinearRightGradientColors[1].x);
		mQualCircle.setGradientEndColor(mLinearRightGradientColors[1].y);
		mQualCircle.setiTranslateListener(new QualCircle.ITranslateListener() {
			@Override
			public void onTranslationStart() {
				Log.d(TAG, "onTranslationStart: ------- ");
			}

			@Override
			public void onTranslation(int percent) {
				Log.d(TAG, "onTranslation: percent " + percent);
			}

			@Override
			public void onTranslationCancel() {
				Log.d(TAG, "onTranslationCancel: ");
			}

			@Override
			public void onTranslationStop() {
				Log.d(TAG, "onTranslationStop: ");
			}

			@Override
			public void onPresetGradientColor(boolean isNext, QualCircle view) {
				int index = (isNext) ? mCurrentIndex.getAndIncrement() : mCurrentIndex.getAndDecrement();
				int startColor = isNext ? mLinearRightGradientColors[index].x : mLinearLeftGradientColors[index].y;
				int endColor = isNext ? mLinearRightGradientColors[index].y : mLinearLeftGradientColors[index].x;
				view.setGradientStartColor(startColor).setGradientEndColor(endColor);
			}
		});
	}

}
