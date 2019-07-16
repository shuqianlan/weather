package com.imou;

import com.ilifesmart.utils.PersistentMgr;
import com.imou.json.*;
import com.imou.json.LeChengRequest;
import io.reactivex.Flowable;
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

	public Flowable<UserTokenResponse> userLogin(String phone) {
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

	public Flowable<LeChengResponse> userBind(String phone, String smsCode) {
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
}
