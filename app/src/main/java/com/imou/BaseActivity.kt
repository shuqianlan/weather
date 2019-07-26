package com.imou

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

open class BaseActivity : AppCompatActivity() {
    inline fun <reified T:AppCompatActivity> startActivity(content:String?=null) {
        val i = Intent(this, T::class.java)// ::class.java类似Java.class
        if (!content.isNullOrBlank()) {
            i.putExtra(Intent.EXTRA_TEXT, content)
        }
        startActivity(i)
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        Dispatchers.Default
        GlobalScope.launch {

        }
    }
}