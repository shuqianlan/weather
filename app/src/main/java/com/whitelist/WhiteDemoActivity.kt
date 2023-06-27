package com.whitelist

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings
import android.widget.Button
import androidx.annotation.RequiresApi
import com.google.android.material.snackbar.Snackbar
import com.ilifesmart.weather.R
import java.lang.Exception

class WhiteDemoActivity : AppCompatActivity() {
    // 加入系统白名单
    private val  REQUEST_CODE:Int = 10090
    private val  REQUEST_LOCATION_CODE:Int = 200
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_white_demo)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!isIgnoringBatteryOptimizations()) {
                requireIgnoreBatteryOptimiaztions()
            }
        }

        findViewById<Button>(R.id.gps_location).setOnClickListener {
            if (!checkGPSIsOpenStatus()) {
                turnToOpenGPRSSetting()
            } else {
                Snackbar.make(findViewById<Button>(R.id.gps_location), "定位已开启", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    // 判断是否在白名单中.
    @RequiresApi(api=Build.VERSION_CODES.M)
    private fun isIgnoringBatteryOptimizations():Boolean {
        var isIgnoring = false

        (getSystemService(Context.POWER_SERVICE) as? PowerManager).also {
            isIgnoring = it?.isIgnoringBatteryOptimizations(packageName) ?: false
        }

        return isIgnoring
    }

    // 加入系统白名单, 允许后台活动
    @RequiresApi(api=Build.VERSION_CODES.M)
    private fun requireIgnoreBatteryOptimiaztions() {
        try {
            Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS).also {
                it.data = Uri.parse("package:" + packageName)
                startActivityForResult(it, REQUEST_CODE)
            }
        } catch (exp:Exception) {

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE) {
            val isGranted = if (resultCode == AppCompatActivity.RESULT_OK) "OK" else "False"
            Snackbar.make(findViewById(R.id.constrain_cont), "权限已授予:"+isGranted, Snackbar.LENGTH_SHORT).show()
        } else if (requestCode == REQUEST_LOCATION_CODE) {
            val isGranted = if (resultCode == AppCompatActivity.RESULT_OK) "OK" else "False"

        }
    }

    private fun checkGPSIsOpenStatus():Boolean {
        var isGPSOpen = false
        (getSystemService(Context.LOCATION_SERVICE) as LocationManager)?.also {
            isGPSOpen = it.isProviderEnabled(LocationManager.GPS_PROVIDER)
        }

        return isGPSOpen
    }

    private fun turnToOpenGPRSSetting() {
        AlertDialog.Builder(this)
            .setMessage("Go to open")
            .setNegativeButton("cancel") { dialog, which ->
                dialog.dismiss()
            }
            .setPositiveButton("ok") { dialog, which ->
                Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).also {
                    startActivityForResult(it, REQUEST_LOCATION_CODE)
                }
            }
            .setCancelable(false)
            .show()
    }
}
