package com.ilifesmart;

import android.Manifest;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.ilifesmart.aop.CheckOnClickActivity;
import com.ilifesmart.cam3drotate.CameraRotateActivity;
import com.ilifesmart.compass.CompassActivity;
import com.ilifesmart.fragment.DialogActivity;
import com.ilifesmart.framelayout.FrameLayoutActivity;
import com.ilifesmart.mapper.MapperActivity;
import com.ilifesmart.miclock.MiClockActivity;
import com.ilifesmart.mvvm.MVVMActivity;
import com.ilifesmart.nature.NatureActivity;
import com.ilifesmart.notification.NotificationActivity;
import com.ilifesmart.os.OSInfoActivity;
import com.ilifesmart.path.CircleActivity;
import com.ilifesmart.preference.SettingActivity;
import com.ilifesmart.receiver.NetChangedBroadcast;
import com.ilifesmart.rxjava.DemoActivity;
import com.ilifesmart.test.SeekBarActivity;
import com.ilifesmart.thread.ThreadTestActivity;
import com.ilifesmart.utils.Utils;
import com.ilifesmart.viewpager.ViewPagerActivity;
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

    private NetChangedBroadcast netChangeReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        mWeather.setPressed(false);
        onCreatView();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        netChangeReceiver = new NetChangedBroadcast();
        registerReceiver(netChangeReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(netChangeReceiver);
    }

    private void onCreatView() {
        Log.d(TAG, "onCreatView: ================== v2");
        int length = TEST_MODULE_INFO.length();
        Log.d(TAG, "onCreatView: length " + length);

        onUpdateView();

        // 获取其它国家key的字符串资源.
//        Resources res = getResources();
//        if (res != null) {
//            String result1 = res.getString(R.string.def_name);
//            String result2 = null;
//            String lang = res.getConfiguration().locale.getLanguage();
//            if (lang.equals("zh")) {
//                res.getConfiguration().locale = Locale.ENGLISH;
//                res.updateConfiguration(res.getConfiguration(), res.getDisplayMetrics());
//                result2 = res.getString(R.string.def_name);
//            }
//
//            Log.d(TAG, "onCreatView: result " + result1 + " result2 " + result2);
//            Log.d(TAG, "onCreatView: ge");
//        }
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

    @OnClick({R.id.weather, R.id.seekbar, R.id.notification, R.id.rotate, R.id.aop_test, R.id.thread_test, R.id.framelayout, R.id.compass, R.id.miclock, R.id.camrotate, R.id.path, R.id.osinfo, R.id.viewpager, R.id.mapper, R.id.dialog, R.id.preference, R.id.nature_ui, R.id.spider_web, R.id.mvvm, R.id.rxjava})
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
            case R.id.thread_test:
                onThread();
                break;
            case R.id.framelayout:
                onFrameLayout();
                break;
            case R.id.compass:
                onCompass();
                break;
            case R.id.miclock:
                onMiClock();
                break;
            case R.id.camrotate:
                onCamRotate();
                break;
            case R.id.path:
                startActivity(Utils.newIntent(this, CircleActivity.class));
                break;
            case R.id.osinfo:
                startActivity(Utils.newIntent(this, OSInfoActivity.class));
                break;
            case R.id.viewpager:
                Utils.startActivity(this, ViewPagerActivity.class);
                break;
            case R.id.mapper:
                Utils.startActivity(this, MapperActivity.class);
                break;
            case R.id.dialog:
                Utils.startActivity(this, DialogActivity.class);
                break;
            case R.id.preference:
//                ImageUtils.clearCachedDrawable();
                Utils.startActivity(this, SettingActivity.class);
                break;
            case R.id.nature_ui:
                Utils.startActivity(this, NatureActivity.class);
                break;
            case R.id.spider_web:
                Utils.startActivity(this, SpiderWebActivity.class);
                break;
            case R.id.mvvm:
                Utils.startActivity(this, MVVMActivity.class);
                break;
            case R.id.rxjava:
                Utils.startActivity(this, DemoActivity.class);
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

    public void onThread() {
        startActivity(ThreadTestActivity.newIntent(this));
    }

    public void onFrameLayout() {
        startActivity(FrameLayoutActivity.newIntent(this));
    }

    public void onCompass() {
        startActivity(Utils.newIntent(this, CompassActivity.class));
    }

    public void onMiClock() {
        startActivity(Utils.newIntent(this, MiClockActivity.class));
    }

    public void onCamRotate() {
        startActivity(Utils.newIntent(this, CameraRotateActivity.class));
    }
}
