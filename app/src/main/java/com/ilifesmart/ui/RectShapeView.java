package com.ilifesmart.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.util.AttributeSet;
import android.view.View;

public class RectShapeView extends View {

	private ShapeDrawable mDrawable;

	public RectShapeView(Context context) {
		this(context, null);
	}

	public RectShapeView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public RectShapeView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		initialize();
	}

	private void initialize() {
		setLayerType(LAYER_TYPE_SOFTWARE, null);

//		BitmapFactory.decodeResource()
//		Bitmap.createBitmap
		Bitmap bitmap;
//		bitmap.createScaledBitmap()
//		bitmap.get()
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		mDrawable = new ShapeDrawable(new RectShape());
		mDrawable.getPaint().setColor(Color.YELLOW);
		mDrawable.getPaint().setStyle(Paint.Style.FILL);
		mDrawable.getBounds().set(new Rect(50, 50, 200, 200));
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		mDrawable.draw(canvas);
	}

}
