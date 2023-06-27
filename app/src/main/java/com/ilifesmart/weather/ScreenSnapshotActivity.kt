package com.ilifesmart.weather

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Point
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.PixelCopy
import android.view.View
import android.widget.Button
import android.widget.ImageView

class ScreenSnapshotActivity : AppCompatActivity() {
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_screen_snapshot)
		
		val point = Point()
		windowManager.defaultDisplay.getSize(point)
		
		findViewById<Button>(R.id.snapshot).setOnClickListener {
			findViewById<View>(android.R.id.content).apply {
				var bitmap:Bitmap?
				if (false && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
					bitmap= Bitmap.createBitmap(point.x, point.y, Bitmap.Config.ARGB_8888)
					PixelCopy.request(window, bitmap, PixelCopy.OnPixelCopyFinishedListener {
						if (it == PixelCopy.SUCCESS) {
							findViewById<ImageView>(R.id.shortcut).background = BitmapDrawable(resources, bitmap)
						}
					}, Handler())
				} else {
					
					Log.d("BBBB", "onCreate: Cache ........ ")
					val rootView = findViewById<View>(android.R.id.content)
//					val rootView = findViewById<View>(android.R.id.content).rootView
					rootView.isDrawingCacheEnabled = true
					rootView.buildDrawingCache()
					bitmap = rootView.getDrawingCache()
					Log.d("BBBB", "onCreate: null ${bitmap == null}")
					findViewById<ImageView>(R.id.shortcut).background = BitmapDrawable(resources, bitmap)
					rootView.isDrawingCacheEnabled = false
				}
			}
			
		}
	}
}