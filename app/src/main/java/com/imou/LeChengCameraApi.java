package com.imou;

import android.widget.RemoteViews;
import com.imou.json.*;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.*;

@RemoteViews.RemoteView
public interface LeChengCameraApi {

    @POST("accessToken")
    Flowable<AccessTokenResponse> accessToken(@Body UserBindSms body);

    @POST("userToken")
    Observable<UserTokenResponse> getUserToken(@Body UserBindSms body);

    @POST("userBindSms") // 发送验证码
    Flowable<LeChengResponse> sendMessageCode(@Body UserBindSms body);

    @POST("userBind")
    Observable<LeChengResponse> userBind(@Body UserBind body);

    @POST("deviceOnline")
    Flowable<DeviceOnlineResponsse> deviceOnline(@Body DeviceOnline body);

    @POST("deviceList")
    Flowable<DeviceListResponse> deviceList(@Body DevicesListRequest body);

    @POST("shareDeviceList")
    Flowable<DeviceListResponse> shareDeviceList(@Body DevicesListRequest body);

    @POST("controlPTZ") // 旧版云台控制
    Flowable<LeChengResponse<String>> controlPTZ(@Body ControlPTZRequest body);

    @POST("controlMovePTZ") //移动，放大，缩小
    Flowable<LeChengResponse<String>> controlMovePTZ(@Body ControlMovePTZRequest body);

    @POST("modifyDeviceAlarmStatus") // 设置动检开关
    Flowable<LeChengResponse<String>> modifyDeviceAlarmStatus(@Body ModifyDeviceAlarmStatusRequest body);

    @POST("upgradeDevice") // 设备升级
    Flowable<LeChengResponse<String>> upgradeDevice(@Body UpgradeDeviceRequest body);

    @POST("recoverSDCard") // SD卡格式化
    Flowable<RecoverSDCardResponse> recoverSDCard(@Body RecoverSDCardRequest body);

    @POST("setDeviceSnap") // 设备抓图
    Flowable<DeviceSnapResponse> setDeviceSnap(@Body DeviceSnapRequest body);

    @POST("setMessageCallback") // 设置时间消息推送
    Flowable<LeChengResponse<String>> setMessageCallback(@Body MessageCallbackRequest body);

    @POST("getMessageCallback") // 获取消息推送Url
    Flowable<Object> getMessageCallback(@Body MessageCallBackGetRequest body);

    @POST("bindDeviceLive")
    Observable<BindDeviceLiveM3U8Response> bindDeviceLive(@Body BindDeviceLiveM3U8Request body);

    @POST("bindDeviceLiveHttps")
    Observable<BindDeviceLiveM3U8Response> bindDeviceLiveHttps(@Body BindDeviceLiveM3U8Request body);

    @POST("bindUserDeviceLive")
    Observable<BindDeviceLiveM3U8Response> bindUserDeviceLive(@Body BindDeviceLiveM3U8Request body);

    @POST("bindUserDeviceLiveHttps")
    Observable<BindDeviceLiveM3U8Response> bindUserDeviceLiveHttps(@Body BindDeviceLiveM3U8Request body);

    @POST("bindRtspLive")
    Observable<BindDeviceLiveM3U8Response> bindRtspLive(@Body BindRtspLiveRequest body);
}
