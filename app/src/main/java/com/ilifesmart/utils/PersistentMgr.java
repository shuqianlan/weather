package com.ilifesmart.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.ilifesmart.ToolsApplication;

/**
 * Created by dusj on 2017/9/15.
 */

public class PersistentMgr {
	static private PersistentMgr s_inst;

	private static final String persistentFileName = "lifesmart_aurora";
	public PersistentMgr inst() {
		if (s_inst == null)
			s_inst = new PersistentMgr();
		return s_inst;
	}
	//SharedPreferences keys
	public static final String IS_FIRST_LAUNCH_AFTER_BOOT = "is_first_launch_after_boot";

	public static boolean putKV(String key, Object value) {
		try {
			SharedPreferences auroraconfig = getPrivateSharedPreferenceFile();
			SharedPreferences.Editor editor = auroraconfig.edit();
			editor.putString(key, (value != null) ? value.toString() : null);
			editor.apply();
		} catch (Exception exp) {
			exp.printStackTrace();
			return false;
		}

		return true;
	}

	public static boolean putKV(String key, int value) {
		try {
			SharedPreferences auroraconfig = getPrivateSharedPreferenceFile();
			SharedPreferences.Editor editor = auroraconfig.edit();
			editor.putInt(key, value);
			editor.apply();
		} catch (Exception exp) {
			exp.printStackTrace();
			return false;
		}

		return true;
	}

	public static boolean putKV(String key, long value) {
		try {
			SharedPreferences auroraconfig = getPrivateSharedPreferenceFile();
			SharedPreferences.Editor editor = auroraconfig.edit();
			editor.putLong(key, value);
			editor.apply();
		} catch (Exception exp) {
			exp.printStackTrace();
			return false;
		}

		return true;
	}

	public static boolean putKV(String key, String value) {
		try {
			SharedPreferences auroraconfig = getPrivateSharedPreferenceFile();
			SharedPreferences.Editor editor = auroraconfig.edit();
			editor.putString(key, value);
			editor.apply();
		} catch (Exception exp) {
			exp.printStackTrace();
			return false;
		}

		return true;
	}

	public static SharedPreferences getPrivateSharedPreferenceFile() {
		return ToolsApplication.getContext().getSharedPreferences(persistentFileName, Context.MODE_PRIVATE);
	}

	public static String readKV(String key) {
		SharedPreferences sharedPreferences = getPrivateSharedPreferenceFile();
		return sharedPreferences.getString(key, null);
	}

	public static String readKV(String key, String def) {
		SharedPreferences sharedPreferences = getPrivateSharedPreferenceFile();
		return sharedPreferences.getString(key, def);
	}

	public static int readKV(String key, int def) {
		SharedPreferences sharedPreferences = getPrivateSharedPreferenceFile();
		return sharedPreferences.getInt(key, def);
	}

	public static long readKV(String key, long def) {
		SharedPreferences sharedPreferences = getPrivateSharedPreferenceFile();
		return sharedPreferences.getLong(key, def);
	}

	public static boolean removeKV(String key) {
		SharedPreferences sharedPreferences = getPrivateSharedPreferenceFile();
		sharedPreferences.edit().remove(key);
		sharedPreferences.edit().commit();

		return true;
	}
}
