package com.imou;

import com.imou.json.*;
import io.reactivex.Flowable;
import retrofit2.http.*;

public interface LeChengCameraApi {

    @POST("userToken")
    Flowable<UserTokenResponse> getUserToken(@Body UserBindSms body);

    @POST("userBindSms") // 发送验证码
    Flowable<LeChengResponse> sendMessageCode(@Body UserBindSms body);

    @POST("userBind")
    Flowable<LeChengResponse> userBind(@Body UserBind body);

    @POST("deviceOnline")
    Flowable<DeviceOnlineResponsse> deviceOnline(@Body DeviceOnline body);

}
