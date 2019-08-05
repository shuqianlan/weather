package com.jetpack.model;

import android.os.SystemClock;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class RecyclerViewModel extends ViewModel {

	private MutableLiveData<List<String>> datas = new MutableLiveData<>();
	private long mInitialTime = SystemClock.elapsedRealtime();
	private List<String> beans = new ArrayList<>();
	Timer timer = new Timer();

	@Inject
	public RecyclerViewModel() {
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				final long newValue = (SystemClock.elapsedRealtime() - mInitialTime)/1000;
				add("Item-"+newValue);
				datas.postValue(getBeans());
			}
		}, 1000, 2000);
	}

	public List<String> getBeans() {
		return beans;
	}

	public void add(String str) {
		beans.add(str);
	}

	public void setBeans(List<String> beans) {
		this.beans = beans;
		datas.postValue(beans);
	}

	@Override
	protected void onCleared() {
		timer.cancel();
		beans.clear(); // 清空内容.
		beans = null;
	}

	public MutableLiveData<List<String>> getDatas() {
		return datas;
	}
}
