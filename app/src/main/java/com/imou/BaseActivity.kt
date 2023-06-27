package com.imou

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle

// inline表示
inline fun <reified T: AppCompatActivity> AppCompatActivity.startActivity(content:String?=null) {
    val i = Intent(this, T::class.java)// ::class.java类似Java.class
    if (!content.isNullOrBlank()) {
        i.putExtra(Intent.EXTRA_TEXT, content)
    }
    startActivity(i)
}

open abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(layoutResId())
        initView()
        initData()
    }

    abstract fun layoutResId(): Int
    open fun initView() {}
    open fun initData() {}
}