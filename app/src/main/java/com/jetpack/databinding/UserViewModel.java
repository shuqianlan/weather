package com.jetpack.databinding;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.concurrent.atomic.AtomicInteger;

public class UserViewModel extends ViewModel {
    private MutableLiveData<String> liveData = new MutableLiveData<>();

    public MutableLiveData<String> getLiveData() {
        return liveData;
    }

    private AtomicInteger atomic = new AtomicInteger(1);
    public String getRandomText() {
        return "Random:" + atomic.getAndIncrement();
    }
}
