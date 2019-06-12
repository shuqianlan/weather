package com.ilifesmart.animation;

import android.animation.Animator;
import android.animation.Keyframe;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.BlurMaskFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ilifesmart.ui.BlurText;
import com.ilifesmart.weather.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AnimationActivity extends AppCompatActivity {

	@BindView(R.id.sample)
	ImageView mSample;
	@BindView(R.id.adddel)
	LinearLayout mAdddel;
	@BindView(R.id.auto)
	LinearLayout mAuto;
	@BindView(R.id.blur_text)
	BlurText mBlurText;

	private Animator mTelephoneAnimator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_animation);
		ButterKnife.bind(this);

		initialize();
	}

	/*
	 * 自定义组内元素的动画效果
	 * APPEARING: 元素出现动画
	 * DISAPPEARING:元素消失动画
	 * CHANGE_APPEARING:元素出现时的其它元素的动画
	 * CHANGE_DISAPPEARING:元素消失时的其它元素的动画
	 * */
	private void initialize() {
		LayoutTransition transition = new LayoutTransition();

		// 若动画集合，可考虑ProperValuesHolder
		Animator animIn = ObjectAnimator.ofFloat(null, "scaleX", 0f, 1.0f);
		transition.setAnimator(LayoutTransition.APPEARING, animIn);

		Animator animOut = ObjectAnimator.ofFloat(null, "scaleX", 1.0f, 0f);
		transition.setAnimator(LayoutTransition.DISAPPEARING, animOut);

		// 此动画的第一个值和最后一个值必须相同.且left与top属性是必须的
		PropertyValuesHolder addChangedLeftHolder = PropertyValuesHolder.ofInt("left", 0, 0);
		PropertyValuesHolder addChangedTopHolder = PropertyValuesHolder.ofInt("top", 0, 0);
		PropertyValuesHolder addChangedScaleHolder = PropertyValuesHolder.ofFloat("scaleX", 0, 1, 0);
		Animator animatorAddChange = ObjectAnimator.ofPropertyValuesHolder(mAuto, addChangedLeftHolder, addChangedTopHolder, addChangedScaleHolder);
		transition.setAnimator(LayoutTransition.CHANGE_APPEARING, animatorAddChange);

		transition.setAnimator(LayoutTransition.CHANGE_DISAPPEARING, null);
		mAuto.setLayoutTransition(transition);
	}

	@OnClick(R.id.start_stop)
	public void onViewClicked() {

		Keyframe frame0 = Keyframe.ofFloat(0.0f, 0);
		Keyframe frame1 = Keyframe.ofFloat(0.1f, -20);
		Keyframe frame2 = Keyframe.ofFloat(0.2f, 20f);
		Keyframe frame3 = Keyframe.ofFloat(0.3f, -20f);
		Keyframe frame4 = Keyframe.ofFloat(0.4f, 20f);
		Keyframe frame5 = Keyframe.ofFloat(0.5f, -20f);
		Keyframe frame6 = Keyframe.ofFloat(0.6f, 20f);
		Keyframe frame7 = Keyframe.ofFloat(0.7f, -20f);
		Keyframe frame8 = Keyframe.ofFloat(0.8f, 20f);
		Keyframe frame9 = Keyframe.ofFloat(0.9f, -20f);
		Keyframe frame10 = Keyframe.ofFloat(1.0f, 0f);

		PropertyValuesHolder holder = PropertyValuesHolder.ofKeyframe("rotation", frame0, frame1, frame2, frame3, frame4, frame5, frame6, frame7, frame8, frame9, frame10);
		mTelephoneAnimator = ObjectAnimator.ofPropertyValuesHolder(mSample, holder);
		mTelephoneAnimator.setDuration(1000);
		((ObjectAnimator) mTelephoneAnimator).setRepeatCount(ValueAnimator.INFINITE);
		((ObjectAnimator) mTelephoneAnimator).setRepeatMode(ValueAnimator.REVERSE);
		mTelephoneAnimator.start();
	}

	@Override
	protected void onPause() {
		super.onPause();

		if (mTelephoneAnimator != null && mTelephoneAnimator.isRunning()) {
			mTelephoneAnimator.cancel();
		}
	}

	@OnClick({R.id.add, R.id.del, R.id.add2, R.id.del2})
	public void onViewClicked(View v) {
		switch (v.getId()) {
			case R.id.add:
				onAdd(mAdddel);
				break;
			case R.id.del:
				onDel(mAdddel);
				break;
			case R.id.add2:
				onAdd(mAuto);
				break;
			case R.id.del2:
				onDel(mAuto);
				break;
		}
	}

	/*
	 * default:onAdd/Del动画
	 * android:animateLayoutChanges="true"
	 * 不可自定义动画效果
	 * */
	public void onAdd(LinearLayout group) {
		Button btn = new Button(this);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
		btn.setLayoutParams(lp);
		group.addView(btn);
	}

	/*
	 * default:onAdd/Del动画
	 * android:animateLayoutChanges="true"
	 * 不可自定义动画效果
	 * */
	public void onDel(LinearLayout group) {
		if (group.getChildCount() != 0) {
			group.removeViewAt(group.getChildCount() - 1);
		}
	}

	@OnClick({R.id.blur_inner, R.id.blur_outer, R.id.blur_normal, R.id.blur_solid})
	public void onBlurStyle(View v) {
		switch (v.getId()) {
			case R.id.blur_inner:
				mBlurText.setMaskFilter(BlurMaskFilter.Blur.INNER);
				break;
			case R.id.blur_outer:
				mBlurText.setMaskFilter(BlurMaskFilter.Blur.OUTER);
				break;
			case R.id.blur_normal:
				mBlurText.setMaskFilter(BlurMaskFilter.Blur.NORMAL);
				break;
			case R.id.blur_solid:
				mBlurText.setMaskFilter(BlurMaskFilter.Blur.SOLID);
				break;
		}
	}

}
