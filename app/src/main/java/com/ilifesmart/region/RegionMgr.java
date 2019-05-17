package com.ilifesmart.region;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegionMgr {

	private static Object mLock = new Object();
	public Map<String, List<RegionItem>> sProvinceMap = new HashMap<>();
	public Map<String, List<RegionItem>> sPopeDomMap = new HashMap<>();
	public Map<String, List<RegionItem>> sTopMap = new HashMap<>();

	private static RegionMgr _instance;
	public static final String TOPKEY = "0";

	private Context mContext;

	public static RegionMgr getInstance() {
		if (_instance == null) {
			synchronized (mLock) {
				if (_instance == null) {
					_instance = new RegionMgr();
				}
			}
		}
		return _instance;
	}

	private RegionMgr() { }

	public void initialize(Context context) {
		AsyncTask.THREAD_POOL_EXECUTOR.execute(new Runnable() {
			@Override
			public void run() {
				try {
					InputStreamReader inputReader = new InputStreamReader( mContext.getAssets().open("config/region/china_region_code.csv") );
					BufferedReader bufReader = new BufferedReader(inputReader);
					String line="";
					while(!TextUtils.isEmpty(line = bufReader.readLine())) {
						dealStringInfo(line);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/*
	* splits
	* Note: 不包含街道名称.
	* 0:省总code,1:市总code,2:县总code,3:省code,4:省名称,5:市code,6:市名称或市辖区,7:县code,8:县名称
	* */
	private void dealStringInfo(String line) {
		if (TextUtils.isEmpty(line)) {
			return;
		}

		String[] splits = line.split(",");
		boolean isCounty = splits.length == 9 && !TextUtils.isEmpty(splits[8]);

		if (isCounty) {
			RegionItem county_bean = new RegionItem.Builder().name(splits[8]).code(splits[2]).parent(splits[1]).index(2).build();
			RegionItem city_bean = new RegionItem.Builder().name(splits[6]).code(splits[1]).parent(splits[0]).index(1).build();
			RegionItem province_bean = new RegionItem.Builder().name(splits[4]).code(splits[0]).parent(TOPKEY).index(0).build();

			addRegionBean(sPopeDomMap, county_bean);
			addRegionBean(sProvinceMap, city_bean);
			addRegionBean(sTopMap, province_bean);
		}

	}

	private void addRegionBean(Map<String, List<RegionItem>> map, RegionItem bean) {
		List<RegionItem> arrays = map.get(bean.getParent());
		if (arrays == null) {
			arrays = new ArrayList<>();
		}

		if (!isContainKey(arrays, bean)) {
			arrays.add(bean);
			map.put(bean.getParent(), arrays);
		}
	}

	private boolean isContainKey(List<RegionItem> beans, RegionItem bean) {
		if (beans == null) {
			return false;
		}

		for(RegionItem item:beans) {
			if (item.getCode().equals(bean.getCode())) {
				return true;
			}
		}

		return false;
	}

	public List<RegionItem> getProvinceRegions() {
		return sTopMap.get(TOPKEY);
	}

	public List<RegionItem> getCityRegions(String province) {
		synchronized (sProvinceMap) {
			return sProvinceMap.get(province);
		}
	}

	public List<RegionItem> getCountyRegions(String city) {
		synchronized (sPopeDomMap) {
			return sPopeDomMap.get(city);
		}
	}

	public List<RegionItem> getRegions(int index) {
		return getRegions(index, null);
	}

	public List<RegionItem> getRegions(int index, String code) {
		List<RegionItem> items = new ArrayList<>();

		switch (index) {
			case 0:
				items = getProvinceRegions();
				break;
			case 1:
				items = getCityRegions(code);
				break;
			case 2:
				items = getCountyRegions(code);
				break;
		}

		return items;
	}

	private RegionItem getRegionItem(Map<String,List<RegionItem>> map, String code) {
		RegionItem bean = null;
		for(Map.Entry<String,List<RegionItem>> entry:map.entrySet()) {
			for(RegionItem item:entry.getValue()) {
				if (item.getCode().equals(code)) {
					bean = item;
					break;
				}
			}
			if (bean != null) {
				break;
			}
		}
		return bean;
	}

	public RegionItem getRegionItem(String code) {
		synchronized (mLock) {
			if (TextUtils.isEmpty(code)) {
				return null;
			}

			RegionItem bean = getRegionItem(sTopMap, code);
			if (bean == null) {
				bean = getRegionItem(sProvinceMap, code);
			}
			if (bean == null) {
				bean = getRegionItem(sPopeDomMap, code);
			}

			return bean;
		}
	}


}
