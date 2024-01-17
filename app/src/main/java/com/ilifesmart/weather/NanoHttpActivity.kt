package com.ilifesmart.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import com.ilifesmart.nanohttp.MyNanoHttp

class NanoHttpActivity : AppCompatActivity() {

    companion object {
        val TAG = "AppCompatActivity"
    }
    private var nanoHttpClient: MyNanoHttp? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nano_http)

        getExternalFilesDir("mgapkg")?.apply {
            nanoHttpClient = MyNanoHttp(8090, absolutePath)
        }
    }

    override fun onResume() {
        super.onResume()

        nanoHttpClient?.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        nanoHttpClient?.closeAllConnections()
    }
}