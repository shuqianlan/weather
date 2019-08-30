package com.jetpack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ilifesmart.weather.R
import kotlinx.android.synthetic.main.activity_live_data.*

class LiveDataActivity : AppCompatActivity() {

    /*
    * 虽然ViewModel中及早的分发了数据36
    * 但Activity声明周期直至onStart以后才接收到了数据36.
    * 可见ViewModel的调用是在Activity达到可见时才接收数据改变(onDataChanged)
    * 便于理解概全整个JetPack.
    * */
    private val viewmodel by lazy {
        ViewModelProviders.of(this).get(LiveDataViewModel::class.java)
    }

    private val builder = StringBuilder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_data)

        viewmodel.livedata.observe(this, Observer {
            println("the result: $it")
            builder.append("the data: $it\n")
            content.text = builder.toString()
        })

        builder.append("onCreate\n")
        content.text = builder.toString()
        println("onCreate")
    }

    override fun onStart() {
        super.onStart()
        builder.append("onStart\n")
        content.text = builder.toString()
        println("onStart")
    }

    override fun onResume() {
        super.onResume()
        builder.append("onResume\n")
        content.text = builder.toString()
        println("onResume")
    }

}
