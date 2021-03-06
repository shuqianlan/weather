package com.ilifesmart;

import android.Manifest;
import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.app.KeyguardManager;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.app.NotificationManagerCompat;

import android.provider.Settings;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.hardware.fingerprint.FingerprintManagerCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.internal.Util;

import com.amap.AmapActivity;
import com.amap.MapLocationActivity;
import com.db.SqliteActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.iid.FirebaseInstanceId;
import com.gson.Gson2Activity;
import com.ilifesmart.animation.AnimationActivity;
import com.ilifesmart.aop.CheckOnClickActivity;
import com.ilifesmart.appbarlayoutdemo.AppBarActivity;
import com.ilifesmart.appbarlayoutdemo.AppBarLiveDataActivity;
import com.ilifesmart.barrage.VideoActivity;
import com.ilifesmart.ble.BluetoothActivity;
import com.ilifesmart.broad.ScreenBroadcastListener;
import com.ilifesmart.cam3drotate.CameraRotateActivity;
import com.ilifesmart.compass.CompassActivity;
import com.ilifesmart.fold.FoldActivity;
import com.ilifesmart.fragment.DialogActivity;
import com.ilifesmart.framelayout.FrameLayoutActivity;
import com.ilifesmart.group.GroupActivity;
import com.ilifesmart.layout.AbstractActivity;
import com.ilifesmart.live.KeepLiveService;
import com.ilifesmart.live.ProgressLiveActivity;
import com.ilifesmart.mapper.MapperActivity;
import com.ilifesmart.miclock.MiClockActivity;
import com.ilifesmart.mvvm.MVVMActivity;
import com.ilifesmart.nature.NatureActivity;
import com.ilifesmart.notification.NotificationActivity;
import com.ilifesmart.os.OSInfoActivity;
import com.ilifesmart.path.CircleActivity;
import com.ilifesmart.preference.SettingActivity;
import com.ilifesmart.receiver.NetChangedBroadcast;
import com.ilifesmart.region.RegionDemoActivity;
import com.ilifesmart.rxjava.DemoActivity;
import com.ilifesmart.test.SeekBarActivity;
import com.ilifesmart.thread.ThreadTestActivity;
import com.ilifesmart.utils.NetWorkUtil;
import com.ilifesmart.utils.PersistentMgr;
import com.ilifesmart.utils.Utils;
import com.ilifesmart.viewpager.ViewPagerActivity;
import com.ilifesmart.weather.ActionBarActivity;
import com.ilifesmart.weather.JobActivity;
import com.ilifesmart.weather.MP3Activity;
import com.ilifesmart.weather.NewFragmentActivity;
import com.ilifesmart.weather.R;
import com.ilifesmart.weather.ScaleDrawableActivity;
import com.ilifesmart.weather.ScreenSnapshotActivity;
import com.ilifesmart.weather.UmengActivity;
import com.ilifesmart.weather.WeatherActivity;
import com.ilifesmart.window.WindowDemoActivity;
import com.imou.*;
import com.jetpack.JetPackActivity;
import com.jni.JniDemoActivity;
import com.kotlin.KotlinDemoActivity;
import com.kotlin.SensorActivity;
import com.layout.LayoutDemoActivity;
import com.media.MediaActivity;
import com.services.HelloServiceActivity;
import com.services.JobScheduleHelper;
import com.spannableText.SpannableActivity;
import com.surfaceview.SurfaceViewActivity;
import com.wanandroid.clipboard.ui.MainActivity;
import com.whitelist.WhiteDemoActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG;
import static androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL;

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

    @Override
    protected void onResume() {
        super.onResume();
    }

    // 判断是否打开了通知监听权限
    private boolean isEnabled() {
        String pkgName = getPackageName();
        final String flat = Settings.Secure.getString(getContentResolver(), "enabled_notification_listeners");
        Log.d(TAG, "isEnabled: FLAT " + flat);
        if (!TextUtils.isEmpty(flat)) {
            final String[] names = flat.split(":");
            Log.d(TAG, "isEnabled: names " + names);
            for (int i = 0; i < names.length; i++) {
                final ComponentName cn = ComponentName.unflattenFromString(names[i]);
                if (cn != null) {
                    if (TextUtils.equals(pkgName, cn.getPackageName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private NetChangedBroadcast netChangeReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);


        onClickAuthBySystem();
        Log.d(TAG, "onCreate: Allowed " + isEnabled());
        String string = Settings.Secure.getString(getContentResolver(), "enabled_notification_listeners");

        Log.d(TAG, "onCreate: notification " + string);

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.w(TAG, "getInstanceId failed", task.getException());
                return;
            }

            // Get new Instance ID token
            String msg = task.getResult().getToken();
            Log.d(TAG, "onCreate: token " + msg);
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        });

        paste();

//        mWeather.setPressed(false);
        onCreatView();

//        mTaoBaoBtn.setOnClickListener(v -> {
//        });
//        startActivity(new Intent(this, MapLocationActivity.class));
//
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
//        netChangeReceiver = new NetChangedBroadcast();
//        registerReceiver(netChangeReceiver, intentFilter);
//
//        final ScreenBroadcastListener.ScreenManager screenManager = ScreenBroadcastListener.ScreenManager.getInstance(this);
//        ScreenBroadcastListener listener = new ScreenBroadcastListener(this);
//        listener.registerListener(new ScreenBroadcastListener.ScreenStateListener() {
//            @Override
//            public void onScreenOn() {
//                Log.d(TAG, "onScreenOn: --------------------- ON");
////                screenManager.finishActivity();
//                startService(new Intent(HomeActivity.this, KeepLiveService.class));
//            }
//
//            @Override
//            public void onScreenOff() {
//                Log.d(TAG, "onScreenOn: --------------------- OFF");
////                screenManager.startActivity();
//            }
//        });
//
//        WifiManager manager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
//        WifiInfo info = manager.getConnectionInfo();
//        int freq = info.getFrequency();
//        Log.d(TAG, "onCreate: freq " + freq);
//        Log.d(TAG, "onCreate: is5G " + NetWorkUtil.is5GHz(freq));
//        Log.d(TAG, "onCreate: is2.4G " + NetWorkUtil.is24GHz(freq));
//
//        scrollText = findViewById(R.id.scroll_text);
//        scrollText.setText("2020-06-23 12:05:27.5877 DEBUG: GLOG: --[[(zai.netmng:75>zai.netmng:341>ai.icttask:341>zai.aienv:21");
//        Log.d(TAG, "onCreate: scrollText " + scrollText.getEllipsize() + " Text " + scrollText.getText().toString());
//        scrollText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int width = scrollText.getWidth();
//                int measureWidth = (int)scrollText.getPaint().measureText(scrollText.getText().toString());
//
//                if (measureWidth <= width) {
//                    return;
//                }
//
//                int offsetDistanceX = Math.abs(measureWidth-width);
//
//                // 可探索法1：根据已转移的translationX，计算出当前显示区域字符串的内容，依次即可.
//
//                scrollText.animate().setDuration(3000).translationX(-offsetDistanceX)
//                .setInterpolator(null)
//                .setUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                    @Override
//                    public void onAnimationUpdate(ValueAnimator animation) {
//                        Log.d(TAG, "onAnimationUpdate: currvalue " + animation.getAnimatedValue());
//                        Log.d(TAG, "onAnimationUpdate: scrollText.transX " + scrollText.getTranslationX());
//                    }
//                })
//                .setListener(new Animator.AnimatorListener() {
//                    @Override
//                    public void onAnimationStart(Animator animation) {
//                        scrollText.setTranslationX(0);
//                    }
//
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        scrollText.setTranslationX(0);
//                    }
//
//                    @Override
//                    public void onAnimationCancel(Animator animation) {
//                        scrollText.setTranslationX(0);
//                    }
//
//                    @Override
//                    public void onAnimationRepeat(Animator animation) {
//                        scrollText.setTranslationX(0);
//                    }
//                })
//                .start();
//            }
//        });
//
//        .setMovementMethod(ScrollingMovementMethod.getInstance());
//        findViewById(R.id.scroll_text).setFocusable(true);

        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {

                String value = getIntent().getExtras().getString(key);

                Log.d(TAG, "Key: " + key + " Value: " + value);

            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        JobScheduleHelper.clearAllJobs(getApplicationContext());
//        unregisterReceiver(netChangeReceiver);
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

    private void onAppbarContext(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view, Gravity.RIGHT | Gravity.END);
        Menu menu = popupMenu.getMenu();
        menu.add(0, Menu.FIRST+1, 1, "LiveData");
        menu.add(1, Menu.FIRST+2, 1, "非LiveData");
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == Menu.FIRST + 1) {
                    Utils.startActivity(HomeActivity.this, AppBarLiveDataActivity.class);
                } else if (item.getItemId() == Menu.FIRST + 2) {
                    Utils.startActivity(HomeActivity.this, AppBarActivity.class);
                }
                return true;
            }
        });
        popupMenu.show();
    }

    private void onClickAuthBySystem() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
            KeyguardManager manager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
            if (!manager.isKeyguardSecure()) {
                Toast.makeText(this, "none set", Toast.LENGTH_LONG).show();
                return;
            }

            Intent i = manager.createConfirmDeviceCredentialIntent(null, null);
            startActivityForResult(i, 10090);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            boolean isSupport = false;

            int target = 0;
            if (Utils.isBiometricSupported(this, BIOMETRIC_STRONG)) {
                isSupport = true;
                target |= BIOMETRIC_STRONG; // 生物功能，依据手机开放，可人脸,指纹，虹膜等
            }

            if (Utils.isBiometricSupported(this, DEVICE_CREDENTIAL)) {
                isSupport = true;
                target |= DEVICE_CREDENTIAL; // PIN或密码
            }

            if (!isSupport) {
                Log.d(TAG, "onClickAuthBySystem: no support");
                return;
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                target = BIOMETRIC_STRONG | DEVICE_CREDENTIAL;
            }
            // 30以前，不支持单独配置DEVICE_CREDENTIAL.
            // 28 or 29, 不支持设置BIOMETRIC_STRONG | DEVICE_CREDENTIAL.

            BiometricPrompt.PromptInfo promptInfo =
                    new BiometricPrompt.PromptInfo.Builder()
                            .setTitle("Biometric login for my app") //设置大标题
                            .setSubtitle("Log in using your biometric credential") // 设置标题下的提示
                            .setAllowedAuthenticators(target)
                            .setConfirmationRequired(true)
                            .build();

            //需要提供的参数callback
            BiometricPrompt biometricPrompt = new BiometricPrompt(this,
                    getMainExecutor(), new BiometricPrompt.AuthenticationCallback() {
                //各种异常的回调
                @Override
                public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                    super.onAuthenticationError(errorCode, errString);
                    Toast.makeText(getApplicationContext(),
                            "Authentication error: " + errString, Toast.LENGTH_SHORT)
                            .show();
                }

                //认证成功的回调
                @Override
                public void onAuthenticationSucceeded(
                        @NonNull BiometricPrompt.AuthenticationResult result) {
                    super.onAuthenticationSucceeded(result);
                    BiometricPrompt.CryptoObject authenticatedCryptoObject =
                            result.getCryptoObject();

                    Log.d(TAG, "onAuthenticationSucceeded: AuthenticationType " + result.getAuthenticationType());
                    // User has verified the signature, cipher, or message
                    // authentication code (MAC) associated with the crypto object,
                    // so you can use it in your app's crypto-driven workflows.
                }

                //认证失败的回调
                @Override
                public void onAuthenticationFailed() {
                    super.onAuthenticationFailed();
                    Toast.makeText(getApplicationContext(), "Authentication failed",
                            Toast.LENGTH_SHORT)
                            .show();
                }
            });

            // 显示认证对话框
            biometricPrompt.authenticate(promptInfo);
        }
    }


    @OnClick({R.id.weather, R.id.seekbar, R.id.notification, R.id.rotate, R.id.aop_test, R.id.thread_test,
            R.id.framelayout, R.id.compass, R.id.miclock, R.id.camrotate, R.id.path, R.id.osinfo, R.id.viewpager, R.id.mapper,
            R.id.dialog, R.id.preference, R.id.nature_ui, R.id.spider_web, R.id.mvvm, R.id.rxjava, R.id.progress,
            R.id.ViewGroup, R.id.live, R.id.curtain, R.id.barrage, R.id.bluetooth, R.id.fold, R.id.region, R.id.span_text,
            R.id.custom_surfaceview, R.id.animation, R.id.window, R.id.abstract_layout, R.id.jni, R.id.miui_right_out, R.id.imou, R.id.gson,
            R.id.sqlite, R.id.jetpack, R.id.layout, R.id.media, R.id.kotlin_conoroutine, R.id.uilayout, R.id.app_bar_ayout, R.id.paged_data, R.id.wanandroid, R.id.service, R.id.white_menu, R.id.echarts,
            R.id.test_for_ui, R.id.smart_plus, R.id.scroll_text, R.id.amap_for_ui, R.id.app_shopping, R.id.encryption,
            R.id.custom_sensor, R.id.toActionBarActivity, R.id.listener_service, R.id.umeng,R.id.new_job,R.id.mp3,
            R.id.scale_drawable,R.id.screenshot,R.id.systemunlock, R.id.new_fragment
    })
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mp3:
                Utils.startActivity(this, MP3Activity.class);
                break;
            case R.id.new_job:
                Utils.startActivity(this, JobActivity.class);
                break;
            case R.id.new_fragment:
                Utils.startActivity(this, NewFragmentActivity.class);
                break;
            case R.id.systemunlock:
                onClickAuthBySystem();
                break;
            case R.id.screenshot:
                Utils.startActivity(this, ScreenSnapshotActivity.class);
                break;
            case R.id.scale_drawable:
                Utils.startActivity(this, ScaleDrawableActivity.class);
                break;
            case R.id.umeng:
                Utils.startActivity(this, UmengActivity.class);
                break;
            case R.id.toActionBarActivity:
                Utils.startActivity(this, ActionBarActivity.class);
                break;
            case R.id.listener_service:
                if (!isEnabled()) {
                    startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
                } else {
                    Snackbar.make(v, "权限已授予", Snackbar.LENGTH_SHORT).show();
                }

                break;
            case R.id.custom_sensor:
                Utils.startActivity(this, SensorActivity.class);
                break;
            case R.id.encryption:
                String nounce = "91641833";
                String ssid = "TP-link";
                String pwd = "12345678";
                String sn = "5F073DAYAJAJ3152A";

                String sha256_encryption_source = nounce + sn + ",";
                String aes256_encryption_source = nounce+","+ssid+","+pwd;
                String shaKey = Utils.shaEnc256(sha256_encryption_source);
                Log.d(TAG, "onClick: shaKey " + shaKey);
                String aes256 = Utils.AES256Encode(aes256_encryption_source, shaKey);
                Log.d(TAG, "onClick: aes256 " + aes256);

                String strBase64 = new String(Base64.encode(aes256.getBytes(), Base64.DEFAULT));
                Log.d(TAG, "onClick: Base64 " + strBase64 + " equals " + "+vwAejYaG3N0yX9wdREEn2CGa1t08NVx4SNVcpfVM6g=".equals(strBase64));

                String result = nounce + "," + strBase64;
                Log.d(TAG, "onClick: qrcode " + result);
                break;
            case R.id.app_shopping:

                Utils.startActivity(this, ShopingActivity.class);
                break;
            case R.id.amap_for_ui:
                Utils.startActivity(this, MapLocationActivity.class);
                break;
            case R.id.scroll_text:
                v.setSelected(false);
                v.setSelected(true);
                break;
            case R.id.smart_plus:
//                Utils.startActivity(this, ArmingActivity.class);
                break;
            case R.id.test_for_ui:
                Utils.startActivity(this, TestUIActivity.class);
                break;
            case R.id.echarts:
                Utils.startActivity(this, com.echarts.DemoActivity.class);
                break;
            case R.id.white_menu:
                Utils.startActivity(this, WhiteDemoActivity.class);
                break;
            case R.id.service:
                Utils.startActivity(this, HelloServiceActivity.class);
                break;
            case R.id.wanandroid:
                Utils.startActivity(this, MainActivity.class);
                break;
            case R.id.paged_data:

                break;
            case R.id.app_bar_ayout:
                onAppbarContext(v);
                break;
            case R.id.uilayout:
                Utils.startActivity(this, UILayoutActivity.class);
                break;
            case R.id.kotlin_conoroutine:
                Utils.startActivity(this, KotlinDemoActivity.class);
                break;
            case R.id.layout:
                Utils.startActivity(this, LayoutDemoActivity.class);
                break;
            case R.id.jetpack:
                Utils.startActivity(this, JetPackActivity.class);
                break;
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
            case R.id.progress:
                Utils.startActivity(this, ProgressActivity.class);
                break;
            case R.id.ViewGroup:
                Utils.startActivity(this, GroupActivity.class);
                break;
            case R.id.live:
                Utils.startActivity(this, ProgressLiveActivity.class);
                break;
            case R.id.curtain:
                Utils.startActivity(this, NatureCurtainActivity.class);
                break;
            case R.id.barrage:
                Utils.startActivity(this, VideoActivity.class);
                break;
            case R.id.bluetooth:
                Utils.startActivity(this, BluetoothActivity.class);
                break;
            case R.id.fold:
                Utils.startActivity(this, FoldActivity.class);
                break;
            case R.id.region:
                Utils.startActivity(this, RegionDemoActivity.class);
                break;
            case R.id.span_text:
                Utils.startActivity(this, SpannableActivity.class);
                break;
            case R.id.custom_surfaceview:
                Utils.startActivity(this, SurfaceViewActivity.class);
                break;
            case R.id.animation:
                Utils.startActivity(this, AnimationActivity.class);
                break;
            case R.id.window:
                Utils.startActivity(this, WindowDemoActivity.class);
                break;
            case R.id.abstract_layout:
                Utils.startActivity(this, AbstractActivity.class);
                break;
            case R.id.jni:
                Utils.startActivity(this, JniDemoActivity.class);
                break;
            case R.id.miui_right_out:
                Utils.startActivity(this, MIUIActivity.class);
                break;
            case R.id.imou:
                String uid = PersistentMgr.readKV(LeChengUtils.LeChengUid, null);
                boolean isOauth = false;
                String token = null;
                try {
                    if (!TextUtils.isEmpty(uid)) {
//                        long expiredTime = PersistentMgr.readKV(LeChengUtils.getLeChengStorgeKey(LeChengCameraWrapInfo.EXTRA_EXPIRETIME), 0l) + 24*3600;
                        token = PersistentMgr.readKV(LeChengUtils.getLeChengStorgeKey(LeChengCameraWrapInfo.EXTRA_USERTOKEN), null);

//                        Log.d(TAG, "onClick: expiredTime " + expiredTime);
                        Log.d(TAG, "onClick: uid " + uid);
                        Log.d(TAG, "onClick: token " + token);
                        isOauth =  !TextUtils.isEmpty(token); // (expiredTime > System.currentTimeMillis()/1000) &&
                    }
                } catch (Exception ex) {
                    isOauth = false;
                    ex.printStackTrace();
                }

                if (isOauth) {
                    LeChengMomgr.getInstance().setToken(token);
                    Intent intent = new Intent(this, DevicesListActivity.class);
                    intent.putExtra(Intent.EXTRA_TEXT, token);
                    startActivity(intent);
                } else {
                    Utils.startActivity(this, LeChengDemoActivity.class);
                }
                break;
            case R.id.gson:
                Utils.startActivity(this, Gson2Activity.class);
                break;
            case R.id.sqlite:
                Utils.startActivity(this, SqliteActivity.class);
                break;
            case R.id.media:
                Utils.startActivity(this, MediaActivity.class);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
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

    public String paste(){
        ClipboardManager manager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        if (manager.hasPrimaryClip() && manager.getPrimaryClip().getItemCount() > 0) {
            CharSequence addedText = manager.getPrimaryClip().getItemAt(0).getText();
            String addedTextString = String.valueOf(addedText);
            if (!TextUtils.isEmpty(addedTextString)) {
                return addedTextString;
            }
        }
        return "";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 10090) {
            Log.d(TAG, "onActivityResult: isOK " + (resultCode == RESULT_OK));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
