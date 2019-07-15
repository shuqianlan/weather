package com.imou

import android.content.Intent
import android.support.v7.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {
    inline fun <reified T:AppCompatActivity> startActivity() {
        val i = Intent(this, T::class.java)
        startActivity(i)
    }
}