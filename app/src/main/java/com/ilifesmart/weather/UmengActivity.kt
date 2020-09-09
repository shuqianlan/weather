package com.ilifesmart.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.umeng.analytics.MobclickAgent
import kotlinx.android.synthetic.main.activity_umeng.*

class UmengActivity : AppCompatActivity() {
	
	companion object {
		val TAG = UmengActivity::class.java
	}
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_umeng)
		
		home_more.setOnClickListener {
			Log.d("BBBB", "onCreate: clicked home_more")
			MobclickAgent.onEventObject(applicationContext, "home_more", mapOf("clicked" to true))
		}
	}
}