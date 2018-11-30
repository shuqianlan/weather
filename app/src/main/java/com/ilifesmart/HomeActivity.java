package com.ilifesmart;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.ilifesmart.aop.CheckOnClickActivity;
import com.ilifesmart.notification.NotificationActivity;
import com.ilifesmart.test.SeekBarActivity;
import com.ilifesmart.weather.R;
import com.ilifesmart.weather.WeatherActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity {

    public static final String TAG = "HomeActivity";
    private static List<String> mPermissions = new ArrayList<>();
    public static final int REQUEST_PERMISSION_CODE = 100;
    private static final String TEST_MODULE_INFO = "HomeActivity";

    static {
        mPermissions.add(Manifest.permission.READ_PHONE_STATE);
        mPermissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        mPermissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    @BindView(R.id.weather)
    Button mWeather;

//    @BindView(R.id.imageview)
//    ImageView mImageview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        mWeather.setPressed(false);
        onCreatView();
    }

    private void onCreatView() {
        Log.d(TAG, "onCreatView: ================== v2");
        int length = TEST_MODULE_INFO.length();
        Log.d(TAG, "onCreatView: length " + length);

        onUpdateView();
    }

    private void onUpdateView() {
        Log.d(TAG, "onUpdateView: OnUpdateView ============== ");
        Log.d(TAG, "onUpdateView: new ============ ");
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (int i = mPermissions.size() - 1; i >= 0; i--) {
                if (checkSelfPermission(mPermissions.get(i)) == PackageManager.PERMISSION_GRANTED) {
                    if (mPermissions.get(i).equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                        ToolsApplication.startLocation();
                    }
                    mPermissions.remove(i);
                }
            }

            if (mPermissions.size() > 0) {
                String[] permissions = mPermissions.toArray(new String[mPermissions.size()]);
                requestPermissions(permissions, REQUEST_PERMISSION_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISSION_CODE) {
            for (int index = 0; index < permissions.length; index++) {
                if (grantResults[index] == PackageManager.PERMISSION_GRANTED) {
                    if (permissions[index].equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                        ToolsApplication.startLocation();
                    }
                }
            }
        }
    }

    @OnClick({R.id.weather, R.id.seekbar, R.id.notification, R.id.rotate, R.id.aop_test})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.weather:
                onWeather();
                break;
            case R.id.seekbar:
                onSeekBar();
                break;
            case R.id.notification:
                onNotification();
                break;
            case R.id.rotate:
                onRotate3D();
                break;
            case R.id.aop_test:
                onAop();
                break;
        }
    }

    private void onWeather() {
        startActivity(WeatherActivity.newIntent(this));
    }

    private void onSeekBar() {
        startActivity(SeekBarActivity.newIntent(this));
    }

    public void onNotification() {
        startActivity(NotificationActivity.newIntent(this));
    }

    public void onRotate3D() {
        startActivity(Rotate3DActivity.newIntent(this));
    }

    public void onAop() {
        startActivity(CheckOnClickActivity.newIntent(this));
    }
}
