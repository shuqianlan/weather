package com.imou;

import com.imou.json.LeChengResponse;
import com.imou.json.UserBind;
import com.imou.json.UserBindSms;
import com.imou.json.UserToken;
import io.reactivex.Flowable;
import retrofit2.http.*;

public interface LeChengCameraApi {

    @POST("userToken")
    Flowable<UserToken> getUserToken(@Body UserBindSms body);

    @POST("userBindSms") // 发送验证码
    Flowable<LeChengResponse> sendMessageCode(@Body UserBindSms body);

    @POST("userBind")
    Flowable<LeChengResponse> userBind(@Body UserBind body);
}
