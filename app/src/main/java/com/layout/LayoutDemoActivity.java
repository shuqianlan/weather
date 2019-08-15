package com.layout;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.ilifesmart.utils.Utils;
import com.ilifesmart.weather.R;

public class LayoutDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_demo);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.coordinator)
    public void onViewClicked() {
        Utils.startActivity(this, LayoutDemo1Activity.class);
    }
}
