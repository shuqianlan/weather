package com.ilifesmart;

import android.Manifest;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.biometric.BiometricPrompt;

import android.os.Environment;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.amap.MapLocationActivity;
import com.db.SqliteActivity;
import com.google.android.material.snackbar.Snackbar;
import com.gson.Gson2Activity;
import com.ilifesmart.animation.AnimationActivity;
import com.ilifesmart.aop.CheckOnClickActivity;
import com.ilifesmart.appbarlayoutdemo.AppBarActivity;
import com.ilifesmart.barrage.VideoActivity;
import com.ilifesmart.ble.BluetoothActivity;
import com.ilifesmart.cam3drotate.CameraRotateActivity;
import com.ilifesmart.compass.CompassActivity;
import com.ilifesmart.fold.FoldActivity;
import com.ilifesmart.fragment.DialogActivity;
import com.ilifesmart.framelayout.FrameLayoutActivity;
import com.ilifesmart.group.GroupActivity;
import com.ilifesmart.layout.AbstractActivity;
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
import com.ilifesmart.utils.A;
import com.ilifesmart.utils.LeCameraUtils;
import com.ilifesmart.utils.PersistentMgr;
import com.ilifesmart.utils.Utils;
import com.ilifesmart.viewpager.ViewPagerActivity;
import com.ilifesmart.weather.ActionBarActivity;
import com.ilifesmart.weather.DeepLinkActivity;
import com.ilifesmart.weather.JobActivity;
import com.ilifesmart.weather.MP3Activity;
import com.ilifesmart.weather.MobileLocationActivity;
import com.ilifesmart.weather.NanoHttpActivity;
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
import com.umeng.analytics.MobclickAgent;
import com.umeng.umcrash.UMCrash;
import com.wanandroid.clipboard.ui.MainActivity;
import com.whitelist.WhiteDemoActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        mPermissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        mPermissions.add(Manifest.permission.MANAGE_EXTERNAL_STORAGE);
    }

    Button mWeather;

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("Home");

        String binary = "11000100111001110011110001010100";
        int sizeInBits = 32; // 指定位数大小（这里假设为 32）

        BitSet bitset = new BitSet();
        for (int i = 0; i < sizeInBits; i++) {
            if (binary.charAt(i) == '1') {
                bitset.set(sizeInBits - i - 1);
            } else {
                bitset.clear(sizeInBits - i - 1);
            }
        }

        float result = Float.intBitsToFloat((bitset.toByteArray())[0]);
        Log.d(TAG, "onResume: result " + result + " ai= " + binaryToFloat(binary));
    }

    // 将二进制字符串转为无符号浮点数
    private float binaryToFloat(String binary) {
        int sizeInBits = 32; // 指定位数大小（这里假设为 32）
        BitSet bitset = new BitSet();
        for (int i = 0; i < sizeInBits; i++) {
            if (binary.charAt(i) == '1') {
                bitset.set(sizeInBits - i - 1);
            }
        }
        return Float.intBitsToFloat(bitset.toByteArray()[0]);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("Home");
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
        mWeather = findViewById(R.id.weather);

        onClickAuthBySystem();
        Log.d(TAG, "onCreate: Allowed " + isEnabled());
        String string = Settings.Secure.getString(getContentResolver(), "enabled_notification_listeners");

        Log.d(TAG, "onCreate: notification " + string);

//        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(task -> {
//            if (!task.isSuccessful()) {
//                Log.w(TAG, "getInstanceId failed", task.getException());
//                return;
//            }
//
//            // Get new Instance ID token
//            String msg = task.getResult().getToken();
//            Log.d(TAG, "onCreate: token " + msg);
//            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
//        });

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

        Log.d(TAG, "onCreate: ================================> ");
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
        MobclickAgent.onKillProcess(getApplicationContext());
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            mPermissions.add(Manifest.permission.POST_NOTIFICATIONS);
        }

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
                } else if (item.getItemId() == Menu.FIRST + 2) {
                    Utils.startActivity(HomeActivity.this, AppBarActivity.class);
                }
                return true;
            }
        });
        popupMenu.show();
    }

    private void onClickAuthBySystem() {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
//            KeyguardManager manager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
//            if (!manager.isKeyguardSecure()) {
//                Toast.makeText(this, "none set", Toast.LENGTH_LONG).show();
//                return;
//            }
//
//            Intent i = manager.createConfirmDeviceCredentialIntent(null, null);
//            startActivityForResult(i, 10090);
//        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//            boolean isSupport = false;
//
//            int target = 0;
//            if (Utils.isBiometricSupported(this, BIOMETRIC_STRONG)) {
//                isSupport = true;
//                target |= BIOMETRIC_STRONG; // 生物功能，依据手机开放，可人脸,指纹，虹膜等
//            }
//
//            if (Utils.isBiometricSupported(this, DEVICE_CREDENTIAL)) {
//                isSupport = true;
//                target |= DEVICE_CREDENTIAL; // PIN或密码
//            }
//
//            if (!isSupport) {
//                Log.d(TAG, "onClickAuthBySystem: no support");
//                return;
//            }
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//                target = BIOMETRIC_STRONG | DEVICE_CREDENTIAL;
//            }
//            // 30以前，不支持单独配置DEVICE_CREDENTIAL.
//            // 28 or 29, 不支持设置BIOMETRIC_STRONG | DEVICE_CREDENTIAL.
//
//            BiometricPrompt.PromptInfo promptInfo =
//                    new BiometricPrompt.PromptInfo.Builder()
//                            .setTitle("Biometric login for my app") //设置大标题
//                            .setSubtitle("Log in using your biometric credential") // 设置标题下的提示
//                            .setAllowedAuthenticators(target)
//                            .setConfirmationRequired(true)
//                            .build();
//
//            //需要提供的参数callback
//            BiometricPrompt biometricPrompt = new BiometricPrompt(this,
//                    getMainExecutor(), new BiometricPrompt.AuthenticationCallback() {
//                //各种异常的回调
//                @Override
//                public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
//                    super.onAuthenticationError(errorCode, errString);
//                    Toast.makeText(getApplicationContext(),
//                            "Authentication error: " + errString, Toast.LENGTH_SHORT)
//                            .show();
//                }
//
//                //认证成功的回调
//                @Override
//                public void onAuthenticationSucceeded(
//                        @NonNull BiometricPrompt.AuthenticationResult result) {
//                    super.onAuthenticationSucceeded(result);
//                    BiometricPrompt.CryptoObject authenticatedCryptoObject =
//                            result.getCryptoObject();
//
//                    Log.d(TAG, "onAuthenticationSucceeded: AuthenticationType " + result.getAuthenticationType());
//                    // User has verified the signature, cipher, or message
//                    // authentication code (MAC) associated with the crypto object,
//                    // so you can use it in your app's crypto-driven workflows.
//                }
//
//                //认证失败的回调
//                @Override
//                public void onAuthenticationFailed() {
//                    super.onAuthenticationFailed();
//                    Toast.makeText(getApplicationContext(), "Authentication failed",
//                            Toast.LENGTH_SHORT)
//                            .show();
//                }
//            });
//
//            // 显示认证对话框
//            biometricPrompt.authenticate(promptInfo);
//        }
    }


    public void onClick(View v) {

        if (v.getId() == R.id.get_system_android_id) {
            String androidid = Settings.System.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
            Log.d(TAG, "onClick: getAndroidID " + androidid);
        } else if (v.getId() == R.id.nanoHttp) {
            Utils.startActivity(this, NanoHttpActivity.class);
        } else if (v.getId() == R.id.lecam_password) {
            try {
//                    LeCameraUtils.check();
                A.main(new String[]{});
            } catch (Exception ex) {

            }
        }
        else if (v.getId() == R.id.deeplink_lsapp) {
            Utils.startActivity(this, DeepLinkActivity.class);
        }
        else if (v.getId() == R.id.location_mobile) {
            Utils.startActivity(this, MobileLocationActivity.class);
        }
        else if (v.getId() == R.id.accessibility) {
            List<ActivityManager.RunningServiceInfo> serviceInfos = ((ActivityManager) getSystemService(ACTIVITY_SERVICE)).getRunningServices(100);
        }
        else if (v.getId() == R.id.openThirdAPP) {
            Utils.startThirdApp("com.ilifesmart.mslict_gp", this);
        }
        else if (v.getId() == R.id.tesla) {
            Utils.startActivity(this, TeslaOauthActivity.class);
        }
        else if (v.getId() == R.id.openAlbum) {
            Utils.startActivity(this, cameraSnapActivity.class);
        }
        else if (v.getId() == R.id.wifi_info) {
            Utils.startActivity(this, WifiInfoActivity.class);
        }
        else if (v.getId() == R.id.umeng_custom_event) {
            Map map = new HashMap();
            map.put("event_id", "setFav");
            map.put("stack", "对于计算型事件不同的参数类型对应不同的计算方式，总共可以分为两大类，数值型和字符型");
            map.put("value", "wtf..");
            MobclickAgent.onEventObject(this, "set_fav", map);

            Map<String, Object> ekvs = new HashMap<String, Object>();
            ekvs.put("type", "popular");
            ekvs.put("artist", "JJLin");
            ekvs.put("type", "popular");
            ekvs.put("artist", "JJLin");
            MobclickAgent.onEventObject(this, "music", ekvs);
        }
        else if (v.getId() == R.id.taobao_shop) {
            Utils.startActivity(this, TaoBaoShopActivity.class);
        }
        else if (v.getId() == R.id.textureViewTest) {
            Utils.startActivity(this, TextureViewTestActivity.class);
        }
        else if (v.getId() == R.id.sharefile) {
            sharePic();
        }
        else if (v.getId() == R.id.webview) {
//            Utils.startActivity(this, WebViewActivity.class);
        }
        else if (v.getId() == R.id.mp3) {
            UMCrash.generateCustomLog("click mpe button =======================", "UMException");
            Utils.startActivity(this, MP3Activity.class);
        }
        else if (v.getId() == R.id.new_job) {
            Utils.startActivity(this, JobActivity.class);
        }
        else if (v.getId() == R.id.new_fragment) {
            Utils.startActivity(this, NewFragmentActivity.class);
        }
        else if (v.getId() == R.id.systemunlock) {
            onClickAuthBySystem();
        }
        else if (v.getId() == R.id.screenshot) {
            Utils.startActivity(this, ScreenSnapshotActivity.class);
        }
        else if (v.getId() == R.id.scale_drawable) {
            Utils.startActivity(this, ScaleDrawableActivity.class);
        }
        else if (v.getId() == R.id.umeng) {
            Utils.startActivity(this, UmengActivity.class);
        }
        else if (v.getId() == R.id.toActionBarActivity) {
            Utils.startActivity(this, ActionBarActivity.class);
        }
        else if (v.getId() == R.id.listener_service) {
            if (!isEnabled()) {
                startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
            } else {
                Snackbar.make(v, "权限已授予", Snackbar.LENGTH_SHORT).show();
            }
        }
        else if (v.getId() == R.id.custom_sensor) {
            Utils.startActivity(this, SensorActivity.class);
        }
        else if (v.getId() == R.id.encryption) {
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
        }
        else if (v.getId() == R.id.app_shopping) {
            Utils.startActivity(this, ShopingActivity.class);
        }
        else if (v.getId() == R.id.amap_for_ui) {
            Utils.startActivity(this, MapLocationActivity.class);
        }
        else if (v.getId() == R.id.scroll_text) {
            v.setSelected(false);
            v.setSelected(true);
        }
        else if (v.getId() == R.id.smart_plus) {
//            Utils.startActivity(this, ArmingActivity.class);
        }
        else if (v.getId() == R.id.test_for_ui) {
            Utils.startActivity(this, TestUIActivity.class);
        }
        else if (v.getId() == R.id.echarts) {
            Utils.startActivity(this, com.echarts.DemoActivity.class);
        }
        else if (v.getId() == R.id.white_menu) {
            Utils.startActivity(this, WhiteDemoActivity.class);
        }
        else if (v.getId() == R.id.service) {
            Utils.startActivity(this, HelloServiceActivity.class);
        }
        else if (v.getId() == R.id.wanandroid) {
            Utils.startActivity(this, MainActivity.class);
        }
        else if (v.getId() == R.id.app_bar_ayout) {
            onAppbarContext(v);
        }
        else if (v.getId() == R.id.uilayout) {
            Utils.startActivity(this, UILayoutActivity.class);
        }
        else if (v.getId() == R.id.kotlin_conoroutine) {
            Utils.startActivity(this, KotlinDemoActivity.class);
        }
        else if (v.getId() == R.id.layout) {
            Utils.startActivity(this, LayoutDemoActivity.class);
        }
        else if (v.getId() == R.id.jetpack) {
            Utils.startActivity(this, JetPackActivity.class);
        }
        else if (v.getId() == R.id.weather) {
            onWeather();
        }
        else if (v.getId() == R.id.seekbar) {
            onSeekBar();
        }
        else if (v.getId() == R.id.notification) {
            onNotification();
        }
        else if (v.getId() == R.id.rotate) {
            onRotate3D();
        }
        else if (v.getId() == R.id.aop_test) {
            onAop();
        }
        else if (v.getId() == R.id.thread_test) {
            onThread();
        }
        else if (v.getId() == R.id.framelayout) {
            onFrameLayout();
        }
        else if (v.getId() == R.id.compass) {
            onCompass();
        }
        else if (v.getId() == R.id.camrotate) {
            onCamRotate();
        }
        else if (v.getId() == R.id.path) {
            Utils.startActivity(this, CircleActivity.class);
        }
        else if (v.getId() == R.id.osinfo) {
            Utils.startActivity(this, OSInfoActivity.class);
        }
        else if (v.getId() == R.id.viewpager) {
            Utils.startActivity(this, ViewPagerActivity.class);
        }
        else if (v.getId() == R.id.mapper) {
            Utils.startActivity(this, MapperActivity.class);
        }
        else if (v.getId() == R.id.dialog) {
            Utils.startActivity(this, DialogActivity.class);
        }
        else if (v.getId() == R.id.preference) {
            Utils.startActivity(this, SettingActivity.class);
        }
        else if (v.getId() == R.id.nature_ui) {
            Utils.startActivity(this, NatureActivity.class);
        }
        else if (v.getId() == R.id.spider_web) {
            Utils.startActivity(this, SpiderWebActivity.class);
        }
        else if (v.getId() == R.id.mvvm) {
            Utils.startActivity(this, MVVMActivity.class);
        }
        else if (v.getId() == R.id.rxjava) {
            Utils.startActivity(this, DemoActivity.class);
        }
        else if (v.getId() == R.id.progress) {
            Utils.startActivity(this, ProgressActivity.class);
        }
        else if (v.getId() == R.id.ViewGroup) {
            Utils.startActivity(this, GroupActivity.class);
        }
        else if (v.getId() == R.id.live) {
            Utils.startActivity(this, ProgressLiveActivity.class);
        }
        else if (v.getId() == R.id.curtain) {
            Utils.startActivity(this, NatureCurtainActivity.class);
        }
        else if (v.getId() == R.id.barrage) {
            Utils.startActivity(this, VideoActivity.class);
        }
        else if (v.getId() == R.id.bluetooth) {
            Utils.startActivity(this, BluetoothActivity.class);
        }
        else if (v.getId() == R.id.fold) {
            Utils.startActivity(this, FoldActivity.class);
        }
        else if (v.getId() == R.id.region) {
            Utils.startActivity(this, RegionDemoActivity.class);
        } else if (v.getId() == R.id.span_text) {
            Utils.startActivity(this, SpannableActivity.class);
        } else if (v.getId() == R.id.custom_surfaceview) {
            Utils.startActivity(this, SurfaceViewActivity.class);
        } else if (v.getId() == R.id.animation) {
            Utils.startActivity(this, AnimationActivity.class);
        } else if (v.getId() == R.id.window) {
            Utils.startActivity(this, WindowDemoActivity.class);
        }else if (v.getId() == R.id.abstract_layout) {
            Utils.startActivity(this, AbstractActivity.class);
        } else if (v.getId() == R.id.jni) {
            Utils.startActivity(this, JniDemoActivity.class);
        } else if (v.getId() == R.id.miui_right_out) {
            Utils.startActivity(this, MIUIActivity.class);
        } else if (v.getId() == R.id.media) {
            Utils.startActivity(this, MediaActivity.class);
        } else if (v.getId() == R.id.sqlite) {
            Utils.startActivity(this, SqliteActivity.class);
        } else if (v.getId() == R.id.gson) {
            Utils.startActivity(this, Gson2Activity.class);
        } else if (v.getId() == R.id.imou) {
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
        }
    }

    private void sharePic() {

        Log.d(TAG, "sharePic: " + Environment.getExternalStorageDirectory().getAbsolutePath());
        File file = new File("/sdcard/lifesmart/shareimages/1562299620.jpeg");
        if (!file.exists()) {
            Toast.makeText(getApplicationContext(), "invalid path", Toast.LENGTH_SHORT).show();;
            return;
        }

        Intent intent = new Intent(Intent.ACTION_SEND);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri contentUri = FileProvider.getUriForFile(this, getPackageName()+".fileprovider", file);
            intent.putExtra(Intent.EXTRA_STREAM, contentUri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_NEW_TASK);

            intent.setType("image/jpeg");
            Intent chooser = Intent.createChooser(intent, "Top 1");
            startActivity(chooser);
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

    public static boolean isSupportCodec(String ffcodecname){
        boolean supportvideo = false;
//        int count = MediaCodecList.getCodecCount();
//        for(int i = 0; i < count; i++)
//        {
//            String[] tyeps = MediaCodecList.getCodecInfoAt(i).getSupportedTypes();
//            for(int j = 0; j < tyeps.length; j++)
//            {
//                if(tyeps[j].equals(findVideoCodecName(ffcodecname)))
//                {
//                    supportvideo = true;
//                    break;
//                }
//            }
//            if(supportvideo)
//            {
//                break;
//            }
//        }
        return supportvideo;
    }
}
