package com.ilifesmart.weather

import android.graphics.drawable.StateListDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_scale_drawable.*

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
			state_list_drawable.background = pressed
		}
		
		// 经验证，selector不支持设置ScaleDrawable
		
	}
}