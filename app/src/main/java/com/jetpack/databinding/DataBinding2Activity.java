package com.jetpack.databinding;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import com.ilifesmart.weather.R;
import com.ilifesmart.weather.databinding.ActivityDataBinding2Binding;

public class DataBinding2Activity extends AppCompatActivity {

    private UserViewModel modelInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        modelInfo = ViewModelProviders.of(this).get(UserViewModel.class);
        ActivityDataBinding2Binding binding = DataBindingUtil.setContentView(this, R.layout.activity_data_binding2);
        binding.setLifecycleOwner(this);
        binding.setModelInfo(modelInfo);
        binding.setHandler(new ButtonHandler());
    }

    public class ButtonHandler {
        public void setRandomText() {
            modelInfo.setName();
        }

        public void reverseVisible() {
            modelInfo.reverVisible();
        }

        public void showRandomText() {
            Log.d("BBBB", "showRandomText: " + modelInfo.getLiveData().getValue());;
        }
    }
}
