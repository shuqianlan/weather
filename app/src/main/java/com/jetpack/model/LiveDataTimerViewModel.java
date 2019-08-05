package com.jetpack.model;

import android.os.SystemClock;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Timer;
import java.util.TimerTask;

public class LiveDataTimerViewModel extends ViewModel {
	private static final int ONE_SECOND = 1000;

	private MutableLiveData<Long> mElapsedTime = new MutableLiveData<>();

	private long mInitialTime;

	public LiveDataTimerViewModel() {
		mInitialTime = SystemClock.elapsedRealtime(); // 返回自启动后的毫秒数
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				final long newValue = (SystemClock.elapsedRealtime() - mInitialTime)/1000;
				mElapsedTime.postValue(newValue);
			}
		}, ONE_SECOND, ONE_SECOND);

	}

	public MutableLiveData<Long> getElapsedTime() {
		return mElapsedTime;
	}

}
