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
}
