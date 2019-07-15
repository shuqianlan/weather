package com.imou

import android.os.Bundle
import android.widget.Button
import com.ilifesmart.weather.R
import com.lechange.opensdk.api.LCOpenSDK_Api

class LeChengDemoActivity : BaseActivity() {

    private lateinit var login:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_le_cheng_demo)

        login = findViewById(R.id.lecheng_user_login)
        LCOpenSDK_Api.setHost(LeChengCameraWrapInfo.APPCNUrl);

        login.setOnClickListener {
            startActivity<LeChengLoginActivity>()
        }
    }

}
