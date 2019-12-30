package com.services

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ilifesmart.weather.R
import kotlinx.android.synthetic.main.activity_hello_service.*

class HelloServiceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hello_service)

        start_service.setOnClickListener {
            Intent(this, HelloService::class.java).also {
                it.putExtra(HelloService.EXTRA_KEY, "Hello,World!")
                it.putExtra(HelloService.EXTRA_AUTO_CLOSE, false)
                startService(it)
            }

        }

        stop_service.setOnClickListener {
            Intent(this, HelloService::class.java).also {
                it.putExtra(HelloService.EXTRA_KEY, "Hello,World!")
                it.putExtra(HelloService.EXTRA_AUTO_CLOSE, true)
                startService(it)
            }

        }


        start_fround_service.setOnClickListener {
            Intent(this, HelloService::class.java).also {
                it.putExtra(HelloService.EXTRA_KEY, "Hello,World!")
                it.putExtra(HelloService.EXTRA_AUTO_CLOSE, false)
                it.putExtra(HelloService.EXTRA_FROUND, true)
                startService(it)
            }
        }


        stop_fround_service.setOnClickListener {
            Intent(this, HelloService::class.java).also {
                it.putExtra(HelloService.EXTRA_KEY, "Hello,World!")
                it.putExtra(HelloService.EXTRA_AUTO_CLOSE, false)
                it.putExtra(HelloService.EXTRA_FROUND, false)
                startService(it)
            }
        }
    }
}