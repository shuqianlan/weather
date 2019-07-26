package com.ilifesmart.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import androidx.appcompat.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.ilifesmart.utils.DensityUtils;
import com.ilifesmart.utils.ImageResizer;
import com.ilifesmart.weather.R;

public class RoundCornerImageView extends AppCompatImageView {
	private static final ImageView.ScaleType SCALE_TYPE = ImageView.ScaleType.CENTER_CROP;
	private int cornerRadius;
	private int mHoverColor = Color.WHITE;

	private RectF mBoundedRectF;
	private RectF mDrawableRect;

	private int resID;
	private boolean once=true;

	private Paint mColorPaint;
	private Path mPath;
	private final PorterDuffXfermode mXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);

	public RoundCornerImageView(Context context) {
		this(context, null);
	}

	public RoundCornerImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public RoundCornerImageView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initizlize(attrs);
	}

	private void initizlize(AttributeSet attrs) {
		cornerRadius = DensityUtils.dp2px(getContext(), 4);
		if (attrs != null) {
			TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.RoundCornerImageView);
			cornerRadius = DensityUtils.dp2px(getContext(), a.getDimension(R.styleable.RoundCornerImageView_radius, 4));
			mHoverColor = a.getColor(R.styleable.RoundCornerImageView_hoverColor, mHoverColor);
			a.recycle();
		}

		mDrawableRect = new RectF();
		mPath = new Path();
		mBoundedRectF = new RectF();
		super.setScaleType(SCALE_TYPE);
		mColorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mBoundedRectF.set(0,0, w, h);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Drawable drawable = getDrawable();
		if (drawable instanceof BitmapDrawable) {
			Paint paint = ((BitmapDrawable)drawable).getPaint();
			setLayerType(View.LAYER_TYPE_HARDWARE, paint);

			Rect bitmapBounds = drawable.getBounds();
			mDrawableRect.set(bitmapBounds);

			int saveCount = canvas.saveLayer(mDrawableRect, null, Canvas.ALL_SAVE_FLAG);
			getImageMatrix().mapRect(mDrawableRect);

			paint.setAntiAlias(true);
			paint.setColor(mHoverColor);
			mPath.reset();
			mPath.addRoundRect(mBoundedRectF, cornerRadius, cornerRadius, Path.Direction.CW);
			canvas.drawPath(mPath, paint);

			Xfermode oldMode = paint.getXfermode();
			paint.setXfermode(mXfermode);
			super.onDraw(canvas);
			paint.setXfermode(oldMode);
			canvas.restoreToCount(saveCount);
		} else {
			mPath.reset();
			mPath.addRoundRect(mBoundedRectF, cornerRadius, cornerRadius, Path.Direction.CW);
			mColorPaint.setColor(mHoverColor);
			canvas.drawPath(mPath, mColorPaint);
		}
	}

	public void setHoverColorAndDrawable(int hoverColor, int resID) {
		this.mHoverColor = hoverColor;
		recycleBitmap();

		if (resID > 0) {
			Bitmap bitmap = ImageResizer.decodeSampleBitmapFromResource(getResources(), resID, getWidth(), getHeight());
			setImageDrawable(new BitmapDrawable(getResources(), bitmap));
		} else {
			setImageDrawable(null);
		}

		invalidate();
	}

	public void setRoundCornerDrawable(int resID) {
		recycleBitmap();

		if (resID > 0) {
			Bitmap bitmap = ImageResizer.decodeSampleBitmapFromResource(getResources(), resID, getWidth(), getHeight());
			setImageDrawable(new BitmapDrawable(getResources(), bitmap));
		} else {
			setImageDrawable(null);
		}
	}

	public void setRoundCornerHoverColor(int color) {
		this.resID = -99;
		this.mHoverColor = color;
		recycleBitmap();
		setImageDrawable(null);
	}

	private void recycleBitmap() {
		Drawable drawable = getDrawable();
		Bitmap bitmap;
		if (drawable instanceof BitmapDrawable) {
			bitmap = ((BitmapDrawable) drawable).getBitmap();
			bitmap.recycle(); // 清理内存.
		}
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		recycleBitmap();
		once=false;
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		if (!once) {
			setRoundCornerDrawable(resID);
		}
	}

}
