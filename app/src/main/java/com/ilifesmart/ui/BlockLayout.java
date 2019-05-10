package com.ilifesmart.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ilifesmart.utils.DensityUtils;
import com.ilifesmart.weather.R;

public class BlockLayout extends LinearLayout {

	private ViewGroup mHeaderCont;
	private TextView mHeaderTitle;
	LinearGradient gradient;

	private Paint mBelowContGradientColorPaint;
	private int startColor = Color.parseColor("#FF646464");
	private int endColor = Color.parseColor("#FF232323");
	private Path mBottomContPath;

	private int mBottomRadius;
	private int mTopRadius;
	private Path mClipPath;

	public BlockLayout(Context context) {
		this(context, null);
	}

	public BlockLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public BlockLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		setWeightSum(5);
		setOrientation(VERTICAL);
		initialize(context, attrs);
	}

	private void initialize(Context context, AttributeSet attrs) {
		mBottomRadius = DensityUtils.dp2px(context, 6.67f);
		mTopRadius = DensityUtils.dp2px(context, 7.33f);

		mHeaderCont = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.nature_launcher_header, this, false);
		addView(mHeaderCont);
		mHeaderTitle = mHeaderCont.findViewById(R.id.block_header_title);

		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.BlockLayout);
		setHeaderTitle(array.getString(R.styleable.BlockLayout_headTitle));
		array.recycle();

		mBelowContGradientColorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mBelowContGradientColorPaint.setStyle(Paint.Style.FILL);

		mBottomContPath = new Path();
		mClipPath = new Path();
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		mClipPath.reset();
		mClipPath.moveTo(0, mTopRadius);
		mClipPath.cubicTo(0, mTopRadius, 0, 0, mTopRadius, 0);
		mClipPath.lineTo(w-mTopRadius, 0);
		mClipPath.cubicTo(w-mTopRadius, 0, w, 0, w, mTopRadius);
		mClipPath.lineTo(w, h-mBottomRadius);
		mClipPath.cubicTo(w, h-mBottomRadius, w, h, w-mBottomRadius, h);
		mClipPath.lineTo(mBottomRadius, h);
		mClipPath.cubicTo(mBottomRadius, h, 0, h, 0, h-mBottomRadius);
		mClipPath.close();

		int headerHeight = h/5;
		gradient = new LinearGradient(0, headerHeight, 0, h, startColor, endColor, Shader.TileMode.CLAMP);
		mBelowContGradientColorPaint.setShader(gradient);

		mBottomContPath.reset();
		mBottomContPath.moveTo(0, headerHeight);
		mBottomContPath.lineTo(w, headerHeight);
		mBottomContPath.lineTo(w, h-mBottomRadius);
		mBottomContPath.cubicTo(w, h-mBottomRadius, w, h, w-mBottomRadius, h);
		mBottomContPath.lineTo(mBottomRadius, h);
		mBottomContPath.cubicTo(mBottomRadius, h, 0, h, 0, h-mBottomRadius);
		mBottomContPath.close();
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		canvas.save();
		canvas.clipPath(mClipPath);
		canvas.drawPath(mBottomContPath, mBelowContGradientColorPaint);
		super.dispatchDraw(canvas);
		canvas.restore();
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		View view = getChildAt(1);
		if (view != null) {
			LayoutParams lp = (LinearLayout.LayoutParams) view.getLayoutParams();
			lp.weight = 4;
			lp.height = 0;
			view.setLayoutParams(lp);
		}

	}

	@Override
	protected void onDisplayHint(int hint) {
		super.onDisplayHint(hint);
	}

	public void setHeaderTitle(String title) {
		if (TextUtils.isEmpty(title)) {
			title = "模块操作";
		}
		mHeaderTitle.setText(title);
	}

}
