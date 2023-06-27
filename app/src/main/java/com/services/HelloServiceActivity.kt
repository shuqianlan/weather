package com.services

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.ilifesmart.weather.R

class HelloServiceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hello_service)

        findViewById<Button>(R.id.start_service).setOnClickListener {
            Intent(this, HelloService::class.java).also {
                it.putExtra(HelloService.EXTRA_KEY, "Hello,World!")
                it.putExtra(HelloService.EXTRA_AUTO_CLOSE, false)
                startService(it)
            }

        }

        findViewById<Button>(R.id.stop_service).setOnClickListener {
            Intent(this, HelloService::class.java).also {
                it.putExtra(HelloService.EXTRA_KEY, "Hello,World!")
                it.putExtra(HelloService.EXTRA_AUTO_CLOSE, true)
                startService(it)
            }

        }


        findViewById<Button>(R.id.start_fround_service).setOnClickListener {
            Intent(this, HelloService::class.java).also {
                it.putExtra(HelloService.EXTRA_KEY, "Hello,World!")
                it.putExtra(HelloService.EXTRA_AUTO_CLOSE, false)
                it.putExtra(HelloService.EXTRA_FROUND, true)
                startService(it)
            }
        }


        findViewById<Button>(R.id.stop_fround_service).setOnClickListener {
            Intent(this, HelloService::class.java).also {
                it.putExtra(HelloService.EXTRA_KEY, "Hello,World!")
                it.putExtra(HelloService.EXTRA_AUTO_CLOSE, false)
                it.putExtra(HelloService.EXTRA_FROUND, false)
                startService(it)
            }
        }
    }
}