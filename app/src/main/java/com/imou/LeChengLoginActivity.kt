package com.imou

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.ilifesmart.weather.R
import io.reactivex.functions.Consumer

class LeChengLoginActivity : BaseActivity() {

    private lateinit var phone:EditText
    private lateinit var userBindSms:Button
    private lateinit var smsCode:EditText
    private lateinit var userBind:Button
    private lateinit var userLogin:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_le_cheng_login)

        phone = findViewById(R.id.lecheng_login_phone)
        userBindSms = findViewById(R.id.send_sms_code)
        userBindSms.setOnClickListener {
            val flowable = RemoteRepository.getInstance().userBindSms(phone.text.toString())
            RxBus.getInstance().doSubscribe(flowable)
                .subscribe(Consumer {
                    println("userBindSms result:$it")
                }, Consumer<Throwable> {
                    println("userBindSms Error:${it.message}")
                })
        }

        smsCode = findViewById(R.id.code)
        userBind = findViewById(R.id.lecheng_login)
        userBind.setOnClickListener {
            val flowable = RemoteRepository.getInstance()
                .userBind(phone.text.toString(), smsCode.text.toString())

            RxBus.getInstance().doSubscribe(flowable)
                .subscribe(Consumer{
                    println("userBind:$it")
                }, Consumer {
                    println("userBind:${it.message}")
                })
        }

        userLogin = findViewById(R.id.lecheng_userToken)
        userLogin.setOnClickListener {
            val flowable = RemoteRepository.getInstance()
                .userLogin(phone.text.toString())

            RxBus.getInstance().doSubscribe(flowable)
                .subscribe(Consumer{
                    println("userBind:$it")
                }, Consumer {
                    println("userBind:${it.message}")
                })
        }
    }
}
