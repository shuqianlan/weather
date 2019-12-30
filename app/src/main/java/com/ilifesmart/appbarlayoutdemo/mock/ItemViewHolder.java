package com.ilifesmart.appbarlayoutdemo.mock;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class ItemViewHolder extends ViewModel {

    private MutableLiveData<List<String>> beans = new MutableLiveData<>();

    public MutableLiveData<List<String>> getBeans() {
        return beans;
    }
}
