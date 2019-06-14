package com.ilifesmart.animation;

import android.animation.Animator;
import android.animation.Keyframe;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ilifesmart.ui.BlurImgageView;
import com.ilifesmart.ui.BlurText;
import com.ilifesmart.ui.CustomDrawable;
import com.ilifesmart.ui.XfermodeView;
import com.ilifesmart.weather.R;

import java.util.Arrays;

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
	@BindView(R.id.xfermode)
	XfermodeView mXfermode;
	@BindView(R.id.custom_image)
	ImageView mCustomImage;
	@BindView(R.id.blur_reshader_image_3)
	ImageView mShineImage;
	@BindView(R.id.blur_alpha_image_2)
	ImageView mShine2Image;
	@BindView(R.id.src_image_1)
	ImageView mShine1Image;
	@BindView(R.id.blur_image)
	BlurImgageView mBlurImage;

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

		/* 自定义CustomDrawable */
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon);
		CustomDrawable drawable = new CustomDrawable(bitmap);
		mCustomImage.setBackground(drawable);

		/* 原始bulb图片 */
		Bitmap srcBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bulb);
		mShine1Image.setImageBitmap(srcBitmap);

		Paint alphaPaint = new Paint();
		BlurMaskFilter filter = new BlurMaskFilter(6, BlurMaskFilter.Blur.NORMAL);
		alphaPaint.setMaskFilter(filter);

		/* 过滤alpha的bulb图片 */
		int[] offset = new int[2];
		Bitmap alphaBitmap = srcBitmap.extractAlpha(alphaPaint, offset);
		mShine2Image.setImageBitmap(alphaBitmap);
		Log.d("Demo", "initialize: offset " + Arrays.toString(offset)); // [-6,-6];即阴影半径

		/* 重新着色的bulb图片 */
		Bitmap shineBitmap = Bitmap.createBitmap(alphaBitmap.getWidth(), alphaBitmap.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(shineBitmap);
		Paint paint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint1.setColor(Color.CYAN);
		canvas.drawBitmap(alphaBitmap, -offset[0], -offset[1], paint1);
		mShineImage.setImageBitmap(shineBitmap);
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
				mBlurImage.setBlurMode(BlurMaskFilter.Blur.INNER);
				break;
			case R.id.blur_outer:
				mBlurText.setMaskFilter(BlurMaskFilter.Blur.OUTER);
				mBlurImage.setBlurMode(BlurMaskFilter.Blur.OUTER);
				break;
			case R.id.blur_normal:
				mBlurText.setMaskFilter(BlurMaskFilter.Blur.NORMAL);
				mBlurImage.setBlurMode(BlurMaskFilter.Blur.NORMAL);
				break;
			case R.id.blur_solid:
				mBlurText.setMaskFilter(BlurMaskFilter.Blur.SOLID);
				mBlurImage.setBlurMode(BlurMaskFilter.Blur.SOLID);
				break;
		}

	}

	@OnClick({R.id.xfermode_clear, R.id.xfermode_src, R.id.xfermode_dst, R.id.xfermode_srcover, R.id.xfermode_dstover, R.id.xfermode_srcin, R.id.xfermode_dstin, R.id.xfermode_srcout, R.id.xfermode_dstout, R.id.xfermode_srcatop, R.id.xfermode_dstatop, R.id.xfermode_xor, R.id.xfermode_darken, R.id.xfermode_lighten, R.id.xfermode_multiply, R.id.xfermode_screen})
	public void onXfermodeClicked(View view) {
		switch (view.getId()) {
			case R.id.xfermode_clear:
				mXfermode.setXfermode(PorterDuff.Mode.CLEAR);
				break;
			case R.id.xfermode_src:
				mXfermode.setXfermode(PorterDuff.Mode.SRC);
				break;
			case R.id.xfermode_dst:
				mXfermode.setXfermode(PorterDuff.Mode.DST);
				break;
			case R.id.xfermode_srcover:
				mXfermode.setXfermode(PorterDuff.Mode.SRC_OVER);
				break;
			case R.id.xfermode_dstover:
				mXfermode.setXfermode(PorterDuff.Mode.DST_OVER);
				break;
			case R.id.xfermode_srcin:
				mXfermode.setXfermode(PorterDuff.Mode.SRC_IN);
				break;
			case R.id.xfermode_dstin:
				mXfermode.setXfermode(PorterDuff.Mode.DST_IN);
				break;
			case R.id.xfermode_srcout:
				mXfermode.setXfermode(PorterDuff.Mode.SRC_OUT);
				break;
			case R.id.xfermode_dstout:
				mXfermode.setXfermode(PorterDuff.Mode.DST_OUT);
				break;
			case R.id.xfermode_srcatop:
				mXfermode.setXfermode(PorterDuff.Mode.SRC_ATOP);
				break;
			case R.id.xfermode_dstatop:
				mXfermode.setXfermode(PorterDuff.Mode.DST_ATOP);
				break;
			case R.id.xfermode_xor:
				mXfermode.setXfermode(PorterDuff.Mode.XOR);
				break;
			case R.id.xfermode_darken:
				mXfermode.setXfermode(PorterDuff.Mode.DARKEN);
				break;
			case R.id.xfermode_lighten:
				mXfermode.setXfermode(PorterDuff.Mode.LIGHTEN);
				break;
			case R.id.xfermode_multiply:
				mXfermode.setXfermode(PorterDuff.Mode.MULTIPLY);
				break;
			case R.id.xfermode_screen:
				mXfermode.setXfermode(PorterDuff.Mode.SCREEN);
				break;
		}
	}

}
