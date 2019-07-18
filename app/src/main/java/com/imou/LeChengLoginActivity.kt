package com.imou

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.ilifesmart.weather.R
import com.imou.json.AccessTokenResponse
import com.imou.json.UserTokenResponse
import io.reactivex.functions.Consumer

class LeChengLoginActivity : BaseActivity() {

    private lateinit var phone:EditText
    private lateinit var userBindSms:Button
    private lateinit var smsCode:EditText
    private lateinit var userBind:Button
    private lateinit var userLogin:Button
    private lateinit var devices:Button
    private lateinit var sharedDevices:Button
    private lateinit var adminLogin:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_le_cheng_login)

        var userToken:String = ""
        phone = findViewById(R.id.lecheng_login_phone)
        userBindSms = findViewById(R.id.send_sms_code)
        userBindSms.setOnClickListener {
            val flowable = RemoteRepository.getInstance().userBindSms(phone.text.toString())
            RxBus.getInstance().doSubscribe(flowable)
                .subscribe(Consumer {
                    println("userBindSms result:$it")
                    Toast.makeText(this@LeChengLoginActivity, "验证码发送成功", Toast.LENGTH_SHORT).show()
                }, Consumer<Throwable> {
                    println("userBindSms Error:${it.message}")
                    Toast.makeText(this@LeChengLoginActivity, "验证码发送失败", Toast.LENGTH_SHORT).show()
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

                    if (it is UserTokenResponse) {
                        val data = it.result.data
                        println("ResultData:$data")
                        if (data is UserTokenResponse.UserTokenResultData) {
                            println("BBBB: ${it.result.data}")
                            userToken = (it.result.data as? UserTokenResponse.UserTokenResultData)?.userToken ?: ""
                        }
                    }
//                    PersistentMgr.putKV("IMOU_ExpiredTime_"+phone.text.toString(), it);
//                    PersistentMgr.putKV("IMOU_UserToken_"+phone.text.toString(), it);
//                    this@LeChengLoginActivity.finish()
                }, Consumer {
                    println("userBind:${it.message}")
                })
        }

        devices = findViewById(R.id.lecheng_devices_list)
        devices.setOnClickListener {
            println("userToken:$userToken")
            val flowable = RemoteRepository.getInstance()
                .deviceList(userToken, "1-10");
            RxBus.getInstance().doSubscribe(flowable)
                .subscribe(Consumer{
                    println("userBind:$it")
                }, Consumer {
                    println("userBind:${it.message}")
                })
        }

        sharedDevices = findViewById(R.id.lecheng_share_devices_list)
        sharedDevices.setOnClickListener {
            val flowable = RemoteRepository.getInstance()
                .shareDeviceList(userToken, "1-10");
            RxBus.getInstance().doSubscribe(flowable)
                .subscribe(Consumer{
                    println("userBind:$it")
                }, Consumer {
                    println("userBind:${it.message}")
                })
        }

        adminLogin = findViewById(R.id.lecheng_admin_userToken)
        adminLogin.setOnClickListener {
            val flowable = RemoteRepository.getInstance().accessToken()

            RxBus.getInstance().doSubscribe(flowable)
                .subscribe(Consumer{
                    println("accessToken:$it")
                    if (it is AccessTokenResponse) {
                        val data = it.result.data
                        userToken = (data as? AccessTokenResponse.ResultData)?.accessToken ?: ""
                        println("accessToken userToken: $userToken")
                    }
                }, Consumer {
                    println("accessToken:${it.message}")
                })
        }
    }
}
