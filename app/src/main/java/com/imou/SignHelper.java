package com.imou;

import com.imou.json.LeChengRequest;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class SignHelper {
	 public static String getSystem(String data, String appId, String appSecret, String sysVersion){
		StringBuffer sign = new StringBuffer();
		try {
			JSONObject params = new JSONObject(data);
			Iterator<?> it = params.keys();
			List<String> keyList = new ArrayList<String>();
			while(it.hasNext()){
				keyList.add(it.next().toString());
			}
			Collections.sort(keyList); // 升序.
			for(String key : keyList){
				sign.append("").append(key).append(":").append(params.get(key).toString()).append(",");
			}
			//System.out.println(sign);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String time = Long.toString(System.currentTimeMillis()/1000);
		String nonce = randomString(32);
		sign.append("time").append(":").append(time).append(",");
		sign.append("nonce").append(":").append(nonce).append(",");
		sign.append("appSecret").append(":").append(appSecret);
		//System.out.println(sign);
		JSONObject system = new JSONObject();
		try {
			system.put("ver", sysVersion);
			system.put("sign", md5Hex(sign.toString()));
			system.put("appId", appId);
			system.put("time", time);
			system.put("nonce", nonce);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return system.toString();
	}

	public static LeChengRequest.SystemBean createSystemBean(Map<String,? extends Object> params) {
		LeChengRequest.SystemBean system = new LeChengRequest.SystemBean();

		StringBuffer sign = new StringBuffer();
		if (params != null) {
			List<String> keyList = new ArrayList<String>();
			for(String key:params.keySet()) {
				keyList.add(key);
			}
			Collections.sort(keyList); // 升序.
			for(String key : keyList){
				sign.append("").append(key).append(":").append(params.get(key).toString()).append(",");
			}
		}

		String time = Long.toString(System.currentTimeMillis()/1000);
		String nonce = randomString(32);
		sign.append("time").append(":").append(time).append(",");
		sign.append("nonce").append(":").append(nonce).append(",");
		sign.append("appSecret").append(":").append(LeChengCameraWrapInfo.AppSecret);
		//System.out.println(sign);
		system.setVer("1.1");
		system.setSign(md5Hex(sign.toString()));
		system.setAppId(LeChengCameraWrapInfo.APPID);
		system.setTime(time);
		system.setNonce(nonce);
		return system;
	}
	 
	 public static String md5Hex(String str) {
			try {
				byte hash[] = MessageDigest.getInstance("MD5").digest(str.getBytes());
				StringBuilder hex = new StringBuilder(hash.length * 2);
				for (byte b : hash) {
					if ((b & 0xFF) < 0x10) {
						hex.append("0");
					}
					hex.append(Integer.toHexString(b & 0xFF));
				}
				return hex.toString();
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
	 
	 final static String VEC = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	 private static Random rand;
	 public static String randomString(int length) {
		rand = new Random(System.currentTimeMillis());
		StringBuilder ret = new StringBuilder();
		for (int i = 0; i < length; i++) {
			ret.append(VEC.charAt(rand.nextInt(VEC.length())));
		}
		return ret.toString();
	 }

	public static String getToken(Map<String,String> args) {
	 	String result = null;
	 	try {
			JSONObject body = new JSONObject();
			JSONObject params = new JSONObject(args);
			body.put("params", params);
			body.put("id", "1");// id号 随机值
			body.put(
					"system",
					new JSONObject(SignHelper.getSystem(params.toString(), LeChengCameraWrapInfo.APPID, LeChengCameraWrapInfo.AppSecret,
							"1.1")));
			result = body.toString();
		} catch (Exception ex) {
	 		ex.printStackTrace();
		}

	 	return result;
	}

	public static String getApiBaseUrl() {
	 	return "https://openapi.lechange.cn:443/openapi/";
	}
}
