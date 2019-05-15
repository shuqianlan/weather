package com.ilifesmart.file;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegionMgr {

	public Map<String, List<RegionItem>> sProvinceMap = new HashMap<>();
	public Map<String, List<RegionItem>> sPopeDomMap = new HashMap<>();
	public Map<String, List<RegionItem>> sCountyMap = new HashMap<>();

	private static RegionMgr _instance;

	private Context mContext;

	public static RegionMgr getInstance(Context context) {
		if (_instance == null) {
			synchronized (_instance) {
				if (_instance == null) {
					_instance = new RegionMgr(context);
				}
			}
		}
		return _instance;
	}

	private RegionMgr(Context context) {
		this.mContext = context;
		initialize();
	}

	public void initialize() {
		try {
			InputStreamReader inputReader = new InputStreamReader( mContext.getAssets().open("config/region/china_region_code.csv") );
			BufferedReader bufReader = new BufferedReader(inputReader);
			String line="";
			String Result="";
			while((line = bufReader.readLine()) != null) {
				Log.d("Line", "initialize: line " + line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void dealStringInfo(String line) {

	}
}
