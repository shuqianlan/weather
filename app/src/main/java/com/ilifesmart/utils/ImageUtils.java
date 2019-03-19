package com.ilifesmart.utils;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.LruCache;

public class ImageUtils {

	public static final String TAG = "ImageUtils";
	private static LruCache<Integer, Drawable> cachedDrawable = new LruCache<>(20);

	public static Drawable getDrawable(int resID, Drawable def) {
		Drawable drawable = cachedDrawable.get(resID);
		return (drawable == null) ? def : drawable;
	}

	public static Drawable getDrawable(int resID) {
		return getDrawable(resID, null);
	}

	public static void putDrawable(int resID, Drawable drawable) {
		cachedDrawable.put(resID, drawable);
	}

	public static void clearCachedDrawable() {
		for (int i = 0; i < cachedDrawable.size(); i++) {
			Drawable drawable = cachedDrawable.get(i);
			if (drawable instanceof BitmapDrawable) {
				Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
				if (bitmap != null) {
					Log.d(TAG, "clearCachedDrawable: bytes " + bitmap.getAllocationByteCount());
					bitmap.recycle();
				}
			}
		}
		cachedDrawable.evictAll();
	}
}
