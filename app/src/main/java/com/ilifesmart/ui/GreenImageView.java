package com.ilifesmart.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import androidx.appcompat.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.ilifesmart.weather.R;

public class GreenImageView extends AppCompatImageView {
	public GreenImageView(Context context) {
		this(context, null);
	}

	public GreenImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public GreenImageView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		setWillNotDraw(false);

		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.GreenImageView);
		int greenAppend = array.getInt(R.styleable.GreenImageView_GreenAppend, 20);
		array.recycle();
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		BitmapDrawable drawable = (BitmapDrawable) (getDrawable());
		Bitmap mSrcBitmap = drawable.getBitmap();
		if (mSrcBitmap != null) {
			Bitmap mDstBitmap = mSrcBitmap.copy(Bitmap.Config.ARGB_8888, true);

			for(int w=0; w<mSrcBitmap.getWidth(); w++) {
				for(int h=0; h < mSrcBitmap.getHeight(); h++) {
					int pixelColor = mSrcBitmap.getPixel(w, h);
					int alpha = Color.alpha(pixelColor);
					int red   = Color.red(pixelColor);
					int green = Color.green(pixelColor);
					int blue  = Color.blue(pixelColor);

					green = Math.min(green+40, 255);
					mDstBitmap.setPixel(w, h, Color.argb(alpha, red, green, blue));
				}
			}

			setImageDrawable(new BitmapDrawable(getResources(), mDstBitmap));

//			if (true) {
//				File fileDir = Environment.getExternalStorageDirectory();
//
//				String path = fileDir.getAbsolutePath().concat("/MyPhoto"); // 根目录下
//
//				File file = new File(path);
//
//				try {
//
//					if (!file.exists()) {
//						file.mkdir();
//					}
//					path += "/slavor.jpg";
//					FileOutputStream stream = new FileOutputStream(new File(path));
//					mDstBitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
//					stream.flush();
//					stream.close();
//				} catch(Exception ex) {
//					ex.printStackTrace();
//				}
//
//			}
		}
	}
}
