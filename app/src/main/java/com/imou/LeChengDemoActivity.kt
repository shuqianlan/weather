package com.imou

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.ilifesmart.utils.PersistentMgr
import com.ilifesmart.weather.R
import com.imou.json.AccessTokenResponse
import com.imou.json.UserTokenResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.atomic.AtomicInteger

class LeChengDemoActivity : BaseActivity() {


    /*
    * 1. 目前已支持设备管理员登录和用户登录
    * 2. 已支持获取当前设备列表(deviceList)
    * 3. 已支持摄像头播放及云台操作
    * 4. 云台控制优化
    * 5. 登录模式及相应的Token数据保存
    * 6. 功能支持:播放、暂停、对讲、录制、动检开关、分辨率切换、升级、消息推送回调、SD卡格式化、设备抓图、云台控制
    * */
    private lateinit var userLogin: Button
    private lateinit var sendCode:Button
    private lateinit var userMobile:EditText
    private lateinit var userSmsCode:EditText

    private lateinit var adminLogin:Button
    private lateinit var adminMobile:EditText

    private lateinit var adminTab:Button
    private lateinit var userTab:Button

    private lateinit var adminCont: ConstraintLayout
    private lateinit var userCont: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_le_cheng_demo)

        userLogin   = findViewById(R.id.style_user_submit)
        sendCode    = findViewById(R.id.send_message)
        userMobile  = findViewById(R.id.style_user_mobile)
        userSmsCode = findViewById(R.id.style_user_smsCode)

        adminLogin  = findViewById(R.id.style_admin_submit)
        adminMobile = findViewById(R.id.style_admin_mobile)
        adminCont   = findViewById(R.id.style_admin)
        userCont    = findViewById(R.id.style_user)

        adminTab    = findViewById(R.id.login_admin)
        userTab     = findViewById(R.id.login_user)

        adminTab.setOnClickListener {
            println("adminTab")
            userTab.isSelected = false
            userCont.visibility = View.INVISIBLE

            it.isSelected = true
            adminCont.visibility = View.VISIBLE
        }

        userTab.setOnClickListener {
            println("userTab")
            adminTab.isSelected = false
            adminCont.visibility = View.INVISIBLE

            it.isSelected = true
            userCont.visibility = View.VISIBLE
        }

        userLogin.setOnClickListener {
            println("userLogin")
            onUserLogin()
        }

        adminLogin.setOnClickListener {
            println("adminLogin")
            onAdminLogin()
        }

        sendCode.setOnClickListener {
            println("sendCode")
            onSendSmsCode()
        }

        userTab.callOnClick()
    }

    private fun checkMobile(mobile:String) = (TextUtils.isEmpty(mobile) || mobile.length != 11)

    // 获取accessToken
    private fun onAdminLogin() {
        val mobile = adminMobile.text.toString()
        if (checkMobile(mobile)) {
            Toast.makeText(this@LeChengDemoActivity, "无效的手机号", Toast.LENGTH_SHORT).show()
            return
        }

        val flowable = RemoteRepository.getInstance().accessToken()
        PersistentMgr.putKV(LeChengUtils.LeChengUid, mobile)

        RxBus.getInstance().doSubscribe(flowable)
            .subscribe(Consumer{
                println("accessToken:$it")
                if (it is AccessTokenResponse) {
                    val data = (it.result.data as? AccessTokenResponse.ResultData)
                    var userToken = data?.accessToken ?: ""
                    var expiretime = System.currentTimeMillis()/1000 + if (data != null) data?.expireTime.toLong() else 0L
                    LeChengMomgr.getInstance().token = userToken
                    println("accessToken userToken: $userToken")
                    println("expireTime: $expiretime")

                    if (!userToken.isNullOrBlank()) {
                        PersistentMgr.putKV(LeChengUtils.getLeChengStorgeKey(LeChengCameraWrapInfo.EXTRA_USERTOKEN), userToken)
                    }

                    PersistentMgr.putKV(LeChengUtils.getLeChengStorgeKey(LeChengCameraWrapInfo.EXTRA_EXPIRETIME), expiretime)
                    startActivity<DevicesListActivity>(userToken);

                }
            }, Consumer {
                println("accessToken:${it.message}")
            })
    }

    @SuppressLint("CheckResult")
    private fun onUserLogin() {
        val mobile = userMobile.text.toString()
        val smsCode =userSmsCode.text.toString()
        if (checkMobile(mobile)) {
            Toast.makeText(this@LeChengDemoActivity, "无效的手机号", Toast.LENGTH_SHORT).show()
            return
        }

        if (tokenCode != OAUTHED && TextUtils.isEmpty(smsCode)) {
            Toast.makeText(this@LeChengDemoActivity, "无效的验证码", Toast.LENGTH_SHORT).show()
            return
        }

        val userBind = RemoteRepository.getInstance()
            .userBind(mobile, smsCode)
        val userLogin = RemoteRepository.getInstance()
            .userLogin(mobile)

        val atomic = AtomicInteger(0)
        Observable.concat(userBind, userLogin)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                Consumer {
                val step = atomic.getAndIncrement()
                if (step == 0) {
                    println("UserBindResult:$it code:${it.result.code} msg:${it.result.msg}")
                } else if (step == 1) {
                    if (it is UserTokenResponse) {
                        if (LeChengUtils.isResponseOK(it)) {
                            val data = it.result.data
                            println("ResultData:$data")
                            if (data is UserTokenResponse.UserTokenResultData) {
                                println("BBBB: ${it.result.data}")
                                val userToken = (it.result.data as? UserTokenResponse.UserTokenResultData)?.userToken
                                val time = ((it.result.data as? UserTokenResponse.UserTokenResultData)?.expireTime ?: 0)
                                println("time: $time data:${it.result.data}")
                                val expiretime:Long = System.currentTimeMillis()/1000+24*3600

                                println("userToken:  $userToken")
                                println("expiretime: $expiretime")
                                if (!userToken.isNullOrBlank()) {
                                    PersistentMgr.putKV(LeChengUtils.getLeChengStorgeKey(LeChengCameraWrapInfo.EXTRA_USERTOKEN), userToken)
                                }

                                PersistentMgr.putKV(LeChengUtils.getLeChengStorgeKey(LeChengCameraWrapInfo.EXTRA_EXPIRETIME), expiretime)
                                startActivity<DevicesListActivity>(userToken);
                            }
                        }
                    }
                }
            }, Consumer {
                    println("Error: $it")
                })
    }

    private val animator = ObjectAnimator.ofInt(0, 60).apply {
        repeatCount = 1
        duration = 60_000
        addUpdateListener(object: ValueAnimator.AnimatorUpdateListener {
            override fun onAnimationUpdate(animation: ValueAnimator?) {
                if ((animation?.animatedValue as? Number) != null) {
                    val time = (animation?.animatedValue as? Number)?.toInt() ?: 0
                    sendCode.text = (60-time).toString()+"S"
                }
            }
        })
    }

    val UNOAUTH = 0
    val OAUTHSucc = 1
    val OAUTHED = 2

    private var tokenCode = UNOAUTH
    private fun onSendSmsCode() {
        if (animator.isRunning) {
            return
        }
        val mobile = userMobile.text.toString()
        if (checkMobile(mobile)) {
            Toast.makeText(this@LeChengDemoActivity, "无效的手机号", Toast.LENGTH_SHORT).show()
            return
        }

        val flowable = RemoteRepository.getInstance().userBindSms(mobile)
        PersistentMgr.putKV(LeChengUtils.LeChengUid, mobile)
        RxBus.getInstance().doSubscribe(flowable)
            .doOnSubscribe(Consumer {
                animator.start()
            })
            .subscribe(Consumer {
                println("userBindSms result:$it")
                println("${it.id}, ${it.result}, ${it.result.code}, ${it.result.msg}")
                if (LeChengUtils.isResponseOK(it)) {
                    tokenCode = OAUTHSucc
                    Toast.makeText(this@LeChengDemoActivity, "验证码发送成功", Toast.LENGTH_SHORT).show()
                } else {
                    tokenCode = OAUTHED
                }

            }, Consumer<Throwable> {
                println("userBindSms Error:${it.message}")
                Toast.makeText(this@LeChengDemoActivity, "验证码发送失败", Toast.LENGTH_SHORT).show()
            })
    }

}
