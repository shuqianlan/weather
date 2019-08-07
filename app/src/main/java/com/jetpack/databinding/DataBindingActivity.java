package com.jetpack.databinding;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;
import com.ilifesmart.weather.R;
import com.ilifesmart.weather.databinding.ActivityDataBindingBinding;

import java.util.concurrent.atomic.AtomicInteger;

public class DataBindingActivity extends AppCompatActivity {

    private User2 user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = new User2();
        user.name = new ObservableField<>("wangxiaomin");
        user.password = new ObservableField<>("123456");
        user.nickName = new ObservableField<>("wangxiaomin");
        user.setAge(0);

        ActivityDataBindingBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_data_binding);
        binding.setUserInfo(user);
        binding.setHandler(new ClickHandler());


    }

    private AtomicInteger atomic = new AtomicInteger(0);
    public class ClickHandler {
        public void onChangeAge() {
            user.setAge(atomic.incrementAndGet());
        }
    }
}
