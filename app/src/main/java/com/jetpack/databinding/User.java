package com.jetpack.databinding;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class User extends BaseObservable {
    @Bindable
    public String name;
    public String nickName;
    public String password;

    private int age;

    @Bindable
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
        notifyPropertyChanged(age);
    }

}
