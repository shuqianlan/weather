package com.imou;

import com.ilifesmart.utils.PersistentMgr;
import com.imou.json.*;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.Retrofit;

import java.util.HashMap;
import java.util.Map;

public class RemoteRepository {

	public static final String TAG = "RemoteRepository";
	private static volatile RemoteRepository sInstance;

	private Retrofit mRetrofit;
	private LeChengCameraApi mLeChengCamApi;

	public static RemoteRepository getInstance() {
		if (sInstance == null) {
			synchronized (RemoteRepository.class) {
				if (sInstance == null) {
					sInstance = new RemoteRepository();
				}
			}
		}

		return sInstance;
	}

	public RemoteRepository() {
		mRetrofit = RetrofitHelper.getInstance().getRetrofit();
		mLeChengCamApi = mRetrofit.create(LeChengCameraApi.class);
	}

	public Observable<UserTokenResponse> userLogin(String phone) {
		UserBindSms body = new UserBindSms();
		UserBindSms.UserBindSmsParams params = new UserBindSms.UserBindSmsParams();
		params.setPhone(phone);
		Map<String,String> args = new HashMap<>();
		args.put("phone", phone);
		LeChengRequest.SystemBean system = SignHelper.createSystemBean(args);

		body.setId("1.1");
		body.setParams(params);
		body.setSystem(system);

		return mLeChengCamApi.getUserToken(body);
	}

	public Flowable<AccessTokenResponse> accessToken() {
		UserBindSms body = new UserBindSms();
		LeChengRequest.ParamsBean params = new LeChengRequest.ParamsBean();
		LeChengRequest.SystemBean system = SignHelper.createSystemBean(null);

		body.setId("1.1");
		body.setParams(params);
		body.setSystem(system);

		return mLeChengCamApi.accessToken(body);
	}

	public Flowable<LeChengResponse> userBindSms(String phone) {
		UserBindSms body = new UserBindSms();
		UserBindSms.UserBindSmsParams params = new UserBindSms.UserBindSmsParams();
		params.setPhone(phone);
		Map<String,String> args = new HashMap<>();
		args.put("phone", phone);
		LeChengRequest.SystemBean system = SignHelper.createSystemBean(args);

		body.setId("1.1");
		body.setParams(params);
		body.setSystem(system);

		return mLeChengCamApi.sendMessageCode(body);
	}

	public Observable<LeChengResponse> userBind(String phone, String smsCode) {
		UserBind body = new UserBind();
		UserBind.UserBindParams params = new UserBind.UserBindParams();
		params.setPhone(phone);
		params.setSmsCode(smsCode);
		Map<String,String> args = new HashMap<>();
		args.put("phone", phone);
		args.put("smsCode", smsCode);
		LeChengRequest.SystemBean system = SignHelper.createSystemBean(args);

		body.setId("1.1");
		body.setParams(params);
		body.setSystem(system);

		return mLeChengCamApi.userBind(body);
	}

	public Flowable<DeviceOnlineResponsse> deviceOnline(String device) {
		DeviceOnline body = new DeviceOnline();
		DeviceOnline.DeviceOnlineParams params = new DeviceOnline.DeviceOnlineParams();

		String token = PersistentMgr.readKV("IMOU_ExpiredTime_18158531684");
		params.setDeviceId(device);
		params.setToken(token);
		Map<String,String> args = new HashMap<>();
		args.put("deviceId", device);
		args.put("token", token);
		LeChengRequest.SystemBean system = SignHelper.createSystemBean(args);

		body.setId("1.1");
		body.setParams(params);
		body.setSystem(system);

		return mLeChengCamApi.deviceOnline(body);
	}

	public Flowable<DeviceListResponse> deviceList(String token, String queryRange) {
		DevicesListRequest body = new DevicesListRequest();
		DevicesListRequest.DeviceListParams params = new DevicesListRequest.DeviceListParams();
		params.setToken(token);
		params.setQueryRange(queryRange);
		body.setId("1.1");
		body.setParams(params);

		Map<String,String> args = new HashMap<>();
		args.put("queryRange", queryRange);
		args.put("token", token);
		LeChengRequest.SystemBean system = SignHelper.createSystemBean(args);
		body.setSystem(system);
		return mLeChengCamApi.deviceList(body);
	}

	public Flowable<DeviceListResponse> shareDeviceList(String token, String queryRange) {
		DevicesListRequest body = new DevicesListRequest();
		DevicesListRequest.DeviceListParams params = new DevicesListRequest.DeviceListParams();
		params.setToken(token);
		params.setQueryRange(queryRange);
		body.setId("1.1");
		body.setParams(params);

		Map<String,String> args = new HashMap<>();
		args.put("queryRange", queryRange);
		args.put("token", token);
		LeChengRequest.SystemBean system = SignHelper.createSystemBean(args);
		body.setSystem(system);
		return mLeChengCamApi.shareDeviceList(body);
	}

	public Flowable<LeChengResponse<String>> controlPTZ(
			String token, String deviceId, String channelId, String operation, double h, double v, double z, String duration
	) {
		ControlPTZRequest body = new ControlPTZRequest();
		body.setId("1.1");
		ControlPTZRequest.ControlPTZRequestParams params = new ControlPTZRequest.ControlPTZRequestParams();
		params.setChannelId(channelId);
		params.setDeviceId(deviceId);
		params.setDuration(duration);
		params.setH(h);
		params.setV(v);
		params.setZ(z);
		params.setOperation(operation);
		params.setToken(token);
		body.setParams(params);

		Map<String,Object> args = new HashMap<>();
		args.put("token", token);
		args.put("deviceId", deviceId);
		args.put("channelId", channelId);
		args.put("operation", operation);
		args.put("h", h);
		args.put("v", v);
		args.put("z", z);
		args.put("duration", duration);
		LeChengRequest.SystemBean system = SignHelper.createSystemBean(args);
		body.setSystem(system);
		return mLeChengCamApi.controlPTZ(body);
	}

	public Flowable<LeChengResponse<String>> controlMovePTZ(
			String token, String deviceId, String channelId, String operation
	) {
		ControlMovePTZRequest body = new ControlMovePTZRequest();
		body.setId("1.1");
		ControlMovePTZRequest.ControlMovePTZParams params = new ControlMovePTZRequest.ControlMovePTZParams();
		params.setChannelId(channelId);
		params.setDeviceId(deviceId);
		params.setDuration(2000L);
		params.setOperation(operation);
		params.setToken(token);
		body.setParams(params);

		Map<String,Object> args = new HashMap<>();
		args.put("token", token);
		args.put("deviceId", deviceId);
		args.put("channelId", channelId);
		args.put("operation", operation);
		args.put("duration", 2000L);
		LeChengRequest.SystemBean system = SignHelper.createSystemBean(args);
		body.setSystem(system);
		return mLeChengCamApi.controlMovePTZ(body);
	}

	public Flowable<LeChengResponse<String>> modifyDeviceAlarmStatus(
			String token, String deviceId, String channelId, boolean enable
	) {
		ModifyDeviceAlarmStatusRequest body = new ModifyDeviceAlarmStatusRequest();
		ModifyDeviceAlarmStatusRequest.Params params = new ModifyDeviceAlarmStatusRequest.Params();
		body.setId("1.1");
		params.setChannelId(channelId);
		params.setDeviceId(deviceId);
		params.setToken(token);
		params.setEnable(enable);

		Map<String,Object> args = new HashMap<>();
		args.put("token", token);
		args.put("deviceId", deviceId);
		args.put("channelId", channelId);
		args.put("enable", enable);
		LeChengRequest.SystemBean system = SignHelper.createSystemBean(args);
		body.setParams(params);
		body.setSystem(system);
		return mLeChengCamApi.modifyDeviceAlarmStatus(body);
	}

	public Flowable<LeChengResponse<String>> upgradeDevice(
			String token, String deviceId
	) {
		UpgradeDeviceRequest body = new UpgradeDeviceRequest();
		UpgradeDeviceRequest.Params params = new UpgradeDeviceRequest.Params();
		params.setDeviceId(deviceId);
		params.setToken(token);
		Map<String,Object> args = new HashMap<>();
		args.put("token", token);
		args.put("deviceId", deviceId);

		LeChengRequest.SystemBean system = SignHelper.createSystemBean(args);
		body.setParams(params);
		body.setSystem(system);
		body.setId("1.1");

		return mLeChengCamApi.upgradeDevice(body);
	}

	public Flowable<RecoverSDCardResponse> recoverSDCard(
			String token, String deviceId, String channelId
	) {
		RecoverSDCardRequest body = new RecoverSDCardRequest();
		RecoverSDCardRequest.Params params = new RecoverSDCardRequest.Params();
		params.setDeviceId(deviceId);
		params.setToken(token);
		params.setChannelId(channelId);
		Map<String,Object> args = new HashMap<>();
		args.put("token", token);
		args.put("deviceId", deviceId);
		args.put("channelId", channelId);

		LeChengRequest.SystemBean system = SignHelper.createSystemBean(args);
		body.setParams(params);
		body.setSystem(system);
		body.setId("1.1");

		return mLeChengCamApi.recoverSDCard(body);
	}

	public Flowable<DeviceSnapResponse> getDeviceSnap(
			String token, String deviceId, String channelId
	) {
		DeviceSnapRequest body = new DeviceSnapRequest();
		DeviceSnapRequest.Params params = new DeviceSnapRequest.Params();
		params.setDeviceId(deviceId);
		params.setToken(token);
		params.setChannelId(channelId);

		Map<String,Object> args = new HashMap<>();
		args.put("token", token);
		args.put("deviceId", deviceId);
		args.put("channelId", channelId);

		LeChengRequest.SystemBean system = SignHelper.createSystemBean(args);
		body.setParams(params);
		body.setSystem(system);
		body.setId("1.1");
		return mLeChengCamApi.setDeviceSnap(body);
	}

	public Flowable<LeChengResponse<String>> setMessageCallback(String token, String callbackFlag) {
		MessageCallbackRequest body = new MessageCallbackRequest();
		MessageCallbackRequest.Params params = new MessageCallbackRequest.Params();
		params.setCallbackFlag(callbackFlag);
		params.setToken(token);

		Map<String,Object> args = new HashMap<>();
		args.put("token", token);
		args.put("status", params.getStatus());
		args.put("callbackUrl", params.getCallbackUrl());
		args.put("callbackFlag", callbackFlag);

		LeChengRequest.SystemBean system = SignHelper.createSystemBean(args);
		body.setParams(params);
		body.setSystem(system);
		body.setId("1.1");
		return mLeChengCamApi.setMessageCallback(body);
	}

	public Flowable<Object> getMessageCallback(String token) {
		MessageCallBackGetRequest body = new MessageCallBackGetRequest();
		MessageCallBackGetRequest.Params params = new MessageCallBackGetRequest.Params();
		params.setToken(token);

		Map<String,Object> args = new HashMap<>();
		args.put("token", token);

		LeChengRequest.SystemBean system = SignHelper.createSystemBean(args);
		body.setParams(params);
		body.setSystem(system);
		body.setId("1.1");
		return mLeChengCamApi.getMessageCallback(body);
	}

	public Observable<BindDeviceLiveM3U8Response> bindDeviceLive(
			String token,String deviceId,String channelId,String liveMode,int streamId
	) {

		BindDeviceLiveM3U8Request body = new BindDeviceLiveM3U8Request();
		BindDeviceLiveM3U8Request.Params params = new BindDeviceLiveM3U8Request.Params();
		params.setChannelId(channelId);
		params.setDeviceId(deviceId);
		params.setLiveMode(liveMode);
		params.setToken(token);
		params.setStreamId(streamId);

		Map<String,Object> args = new HashMap<>();
		args.put("token", token);
		args.put("deviceId", deviceId);
		args.put("channelId", channelId);
		args.put("liveMode", liveMode);
		args.put("streamId", streamId);

		LeChengRequest.SystemBean system = SignHelper.createSystemBean(args);
		body.setParams(params);
		body.setSystem(system);
		body.setId("1.1");
		return mLeChengCamApi.bindDeviceLive(body);
	}

	public Observable<BindDeviceLiveM3U8Response> bindDeviceLiveHttps(
			String token,String deviceId,String channelId,String liveMode,int streamId
	) {

		BindDeviceLiveM3U8Request body = new BindDeviceLiveM3U8Request();
		BindDeviceLiveM3U8Request.Params params = new BindDeviceLiveM3U8Request.Params();
		params.setChannelId(channelId);
		params.setDeviceId(deviceId);
		params.setLiveMode(liveMode);
		params.setToken(token);
		params.setStreamId(streamId);

		Map<String,Object> args = new HashMap<>();
		args.put("token", token);
		args.put("deviceId", deviceId);
		args.put("channelId", channelId);
		args.put("liveMode", liveMode);
		args.put("streamId", streamId);

		LeChengRequest.SystemBean system = SignHelper.createSystemBean(args);
		body.setParams(params);
		body.setSystem(system);
		body.setId("1.1");
		return mLeChengCamApi.bindDeviceLiveHttps(body);
	}

	public Observable<BindDeviceLiveM3U8Response> bindUserDeviceLive(
			String token,String deviceId,String channelId,String liveMode,int streamId
	) {

		BindDeviceLiveM3U8Request body = new BindDeviceLiveM3U8Request();
		BindDeviceLiveM3U8Request.Params params = new BindDeviceLiveM3U8Request.Params();
		params.setChannelId(channelId);
		params.setDeviceId(deviceId);
		params.setLiveMode(liveMode);
		params.setToken(token);
		params.setStreamId(streamId);

		Map<String,Object> args = new HashMap<>();
		args.put("token", token);
		args.put("deviceId", deviceId);
		args.put("channelId", channelId);
		args.put("liveMode", liveMode);
		args.put("streamId", streamId);

		LeChengRequest.SystemBean system = SignHelper.createSystemBean(args);
		body.setParams(params);
		body.setSystem(system);
		body.setId("1.1");
		return mLeChengCamApi.bindUserDeviceLive(body);
	}

	public Observable<BindDeviceLiveM3U8Response> bindUserDeviceLiveHttps(
			String token,String deviceId,String channelId,String liveMode,int streamId
	) {

		BindDeviceLiveM3U8Request body = new BindDeviceLiveM3U8Request();
		BindDeviceLiveM3U8Request.Params params = new BindDeviceLiveM3U8Request.Params();
		params.setChannelId(channelId);
		params.setDeviceId(deviceId);
		params.setLiveMode(liveMode);
		params.setToken(token);
		params.setStreamId(streamId);

		Map<String,Object> args = new HashMap<>();
		args.put("token", token);
		args.put("deviceId", deviceId);
		args.put("channelId", channelId);
		args.put("liveMode", liveMode);
		args.put("streamId", streamId);

		LeChengRequest.SystemBean system = SignHelper.createSystemBean(args);
		body.setParams(params);
		body.setSystem(system);
		body.setId("1.1");
		return mLeChengCamApi.bindUserDeviceLiveHttps(body);
	}

	public Observable<BindDeviceLiveM3U8Response> bindRtspLive(String token) {

		BindRtspLiveRequest body = new BindRtspLiveRequest();
		BindRtspLiveRequest.Params params = new BindRtspLiveRequest.Params();
		params.setToken(token);

		Map<String,Object> args = new HashMap<>();
		args.put("token", token);
		args.put("streamUrl", params.getStreamUrl());

		LeChengRequest.SystemBean system = SignHelper.createSystemBean(args);
		body.setParams(params);
		body.setSystem(system);
		body.setId("1.1");
		return mLeChengCamApi.bindRtspLive(body);
	}


}
