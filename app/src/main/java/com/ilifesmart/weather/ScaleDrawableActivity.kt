package com.ilifesmart.weather

import android.graphics.drawable.StateListDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout

class ScaleDrawableActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_scale_drawable)
		
		var listDrawable = StateListDrawable()
		val normal = getDrawable(R.drawable.bulb_scale_50)
		val pressed = getDrawable(R.drawable.bulb_scale_80)
		
		with(listDrawable) {
			addState(intArrayOf(android.R.attr.state_pressed), pressed)
			addState(intArrayOf(), normal)
		}.apply {
			findViewById<FrameLayout>(R.id.state_list_drawable).background = pressed
		}
		
		// 经验证，selector不支持设置ScaleDrawable
		
	}
}