package com.ilifesmart.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.StateListDrawable;
import androidx.appcompat.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;

public class StrokeImageView extends AppCompatImageView {

	public static final String TAG = "StrokeImageView";

	public StrokeImageView(Context context) {
		this(context, null);
	}

	public StrokeImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public StrokeImageView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		setClickable(true);
	}

	/*
	*
	* Finalize inflating a view from XML. This is called as the last phase
	* of inflation, after all child views have been added.
	*
	* */
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(Color.CYAN);

		setStateDrawable(paint);
	}

	private void setStateDrawable(Paint paint) {
		BitmapDrawable drawable = (BitmapDrawable) getDrawable();
		Bitmap bitmap = drawable.getBitmap();

		Log.d(TAG, "setStateDrawable: byteCount (M) " + bitmap.getAllocationByteCount()/1024f/1024);

		Bitmap strokeBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(strokeBitmap);
		canvas.drawBitmap(bitmap.extractAlpha(), 0, 0, paint);
		Log.d(TAG, "setStateDrawable: byteCount (M) " + strokeBitmap.getAllocationByteCount()/1024f/1024);

		/*
		* Blur
		* .NORMAL: Blur inside and outside the original border
		* .SOLID:  Draw solid inside the border, blur outside.
		* .OUTER:  Draw nothing inside the border, blur outside.
		* .INNER:  Blur inside the border, draw nothing outside.
		* */
		BlurMaskFilter filter = new BlurMaskFilter(20, BlurMaskFilter.Blur.NORMAL);

		StateListDrawable state = new StateListDrawable();
		state.addState(new int[] {android.R.attr.state_pressed}, new BitmapDrawable(getResources(), strokeBitmap));
		state.addState(new int[] {}, drawable);

		setBackground(state);
	}


}
