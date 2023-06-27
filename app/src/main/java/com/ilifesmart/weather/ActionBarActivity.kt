package com.ilifesmart.weather

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.spannableText.MyClickableSpan

class ActionBarActivity : AppCompatActivity() {
	
	companion object {
		val TAG = "ActionBarActivity"
	}
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_action_bar)
		
		this.supportActionBar?.setDisplayShowHomeEnabled(true)
		this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
		
		initView()
	}
	
	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		val result = when (item.itemId) {
			android.R.id.home -> {
				finish()
				true
			}
			else -> false
		}
		return result or super.onOptionsItemSelected(item)
	}
	
	private val policy_text = "Review Google's Privacy Policy"
	private val prefix_policy = "Review Google's"
	private fun initView() {
		val string = SpannableString(policy_text)

		val span = ForegroundColorSpan(Color.rgb(0, 0xa8, 0x86))
		string.setSpan(span, prefix_policy.length, policy_text.length, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
		
		// 此处需自定义此Click事件.
		val clickSpan = MyClickableSpan(policy_text)
		string.setSpan(clickSpan, prefix_policy.length, policy_text.length, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
		
		val underlineSpanSpan = UnderlineSpan()
		string.setSpan(underlineSpanSpan, prefix_policy.length, policy_text.length, Spanned.SPAN_INCLUSIVE_INCLUSIVE)
		
		findViewById<TextView>(R.id.policy).movementMethod = LinkMovementMethod.getInstance()
		findViewById<TextView>(R.id.policy).text = string

		findViewById<TextView>(R.id.policy).setOnClickListener {
			Log.d(TAG, "initView: clicked policy")
		}
	}
}