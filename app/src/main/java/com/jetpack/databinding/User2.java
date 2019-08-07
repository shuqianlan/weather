package com.jetpack.databinding;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;

public class User2 {
    public ObservableField<String> name = new ObservableField<>();
    public ObservableField<String> nickName;
    public ObservableField<String> password;

    private ObservableInt age = new ObservableInt();

    public void setAge(int age) {
        Log.d("BBBB", "setAge: " + age);
        this.age.set(age);
        Log.d("BBBB", "this: " + this);
    }

    public ObservableInt getAge() {
        return age;
    }

    @NonNull
    @Override
    public String toString() {
        return new StringBuilder().append("name:").append(name.get())
                .append(",nick:").append(nickName.get())
                .append(",password:").append(password.get())
                .append(",age:").append(age.get()).toString();
    }
}
