package com.jetpack.databinding;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.concurrent.atomic.AtomicInteger;

public class UserViewModel extends ViewModel {
    private MutableLiveData<DataBean> liveData = new MutableLiveData<>();

    private DataBean bean = new DataBean();

    public MutableLiveData<DataBean> getLiveData() {
        return liveData;
    }

    private AtomicInteger atomic = new AtomicInteger(1);
    public String getRandomText() {
        return "Random:" + atomic.getAndIncrement();
    }

    public void setName() {
        bean.name = getRandomText();
        liveData.setValue(bean);
    }

    public void reverVisible() {
        bean.isGone = !bean.isGone;
        liveData.setValue(bean);
    }

}
