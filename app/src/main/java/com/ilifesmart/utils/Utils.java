package com.ilifesmart.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.graphics.Bitmap;
import android.os.Build;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;

import com.ilifesmart.weather.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import wseemann.media.FFmpegMediaMetadataRetriever;

public class Utils {

    private static long lastClickTimeMills;
    private final static int SPACE_TIME = 300;

    public synchronized static boolean isDoubleClick() {
        long currentTime = System.currentTimeMillis();
        boolean isDoubleClick = false;

        if ((currentTime - lastClickTimeMills) < SPACE_TIME) {
            isDoubleClick = true;
        }

        lastClickTimeMills = currentTime;
        return isDoubleClick;
    }

    public static Intent newIntent(Context context, Class<? extends AppCompatActivity> cls) {
        return new Intent(context, cls);
    }

    public static void startActivity(Context context, Class<? extends AppCompatActivity> cls) {
        context.startActivity(newIntent(context, cls));
    }

    public static AlertDialog creatLoadingDialog(Context context) {
        View v = LayoutInflater.from(context).inflate(R.layout.fragment_loading_dialog, null);
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(v)
                .create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    // 文件拷贝.
    public static void copyFile(InputStream in, String path) {
        try {
            FileOutputStream out = new FileOutputStream(new File(path));
            byte[] buffer = new byte[1024];
            int byteCount = 0;
            while((byteCount = in.read(buffer)) != -1) {
                out.write(buffer, 0, byteCount);
            }

            out.flush();
            in.close();
            out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static Bitmap getVideoThumbnail(InputStream in, String filePath) {
        return getVideoThumbnail(in, filePath, 16000000);
    }

    public static Bitmap getVideoThumbnail(InputStream in, String path, int time) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        copyFile(in, path);
        //implementation 'com.github.wseemann:FFmpegMediaMetadataRetriever-armeabi-v7a:1.0.14'
        FFmpegMediaMetadataRetriever retriever = new FFmpegMediaMetadataRetriever();
        retriever.setDataSource(path);
        retriever.extractMetadata(FFmpegMediaMetadataRetriever.METADATA_KEY_ALBUM);
        retriever.extractMetadata(FFmpegMediaMetadataRetriever.METADATA_KEY_ARTIST);
        Bitmap mp = retriever.getFrameAtTime(time, FFmpegMediaMetadataRetriever.OPTION_CLOSEST);
        retriever.release();

        return mp;
    }

    public static String getUUID() {
        // 新的获取设备唯一硬件编码的函数
        String serial = null;

        String m_szDevIDShort = "35" +
                Build.BOARD.length() % 10 + Build.BRAND.length() % 10 +

                Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 +

                Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 +

                Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 +

                Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 +

                Build.TAGS.length() % 10 + Build.TYPE.length() % 10 +

                Build.USER.length() % 10; //13 位

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                serial = android.os.Build.getSerial();
            } else {
                serial = Build.SERIAL;
            }
            //API>=9 使用serial号
            return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
        } catch (Exception exception) {
            //serial需要一个初始化
            serial = "serial"; // 随便一个初始化
        }
        //使用硬件信息拼凑出来的15位号码
        return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
    }

    public static final String TAG = "Encryption";
    public static String shaEnc256(String strSrc) {
        MessageDigest md = null;
        String strDes = null;
        byte[] bt = strSrc.getBytes();
        try {
            md = MessageDigest.getInstance("SHA-256");// 将此换成SHA-1、SHA-512、SHA-384等参数
            md.update(bt);
            byte[] bytes = md.digest();
            strDes = bytes2Hex(bytes); // to HexString
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        return strDes;
    }

    public static String bytes2Hex(byte[] bts) {
        Log.d(TAG, "bytes2Hex: source_value " + new String(bts));

        Log.d(TAG, "bytes2Hex: 916418335F073DAYAJAJ3152A " + "916418335F073DAYAJAJ3152A".equals(new String(bts)));
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            Log.d(TAG, "bytes2Hex: TMP " + tmp + " src " + String.format("%X", bts[i]));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }

    public static String AES256Encode(String stringToEncode, String keyString)
            throws NullPointerException {
        if (keyString.length() == 0 || keyString == null) {
            return null;
        }
        if (stringToEncode.length() == 0 || stringToEncode == null) {
            return null;
        }
        try {
            SecretKeySpec skeySpec = getKey(keyString);
            byte[] data = stringToEncode.getBytes("UTF8");
            final byte[] iv = new byte[16];
            Arrays.fill(iv, (byte) 0x00);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivParameterSpec);
            String encrypedValue = Base64.encodeToString(cipher.doFinal(data),
                    Base64.DEFAULT);
            return encrypedValue;
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static SecretKeySpec getKey(String password)
            throws UnsupportedEncodingException {
        // 如果为128将长度改为128即可
        int keyLength = 256;
        byte[] keyBytes = new byte[keyLength / 8];
        // explicitly fill with zeros
        Arrays.fill(keyBytes, (byte) 0x0);
        byte[] passwordBytes = toByte(password);
        int length = passwordBytes.length < keyBytes.length ? passwordBytes.length
                : keyBytes.length;
        System.arraycopy(passwordBytes, 0, keyBytes, 0, length);
        SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
        return key;
    }

    private static byte[] toByte(String hexString) {
        int len = hexString.length() / 2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++)
            result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2),
                    16).byteValue();
        return result;
    }

    public static boolean isBiometricSupported(Context context, int type) {
        boolean result = false;

        BiometricManager manager = BiometricManager.from(context);
        switch (manager.canAuthenticate(type)) {
            case BiometricManager.BIOMETRIC_SUCCESS:
                result = true;
                break;
        }

        return result;
    }

    private static boolean isThirdAPPInstalled(String pkg, Context context) {
        PackageInfo info = null;
        try {
            info = context.getPackageManager().getPackageInfo(pkg, 0);
        } catch (Exception ex) {

        }

        return info != null;
    }

    public static void startThirdApp(String pkg, Context context) {
        if (!isThirdAPPInstalled(pkg, context)) {
            Toast.makeText(context, "APP未安装", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = context.getPackageManager().getLaunchIntentForPackage(pkg);
        if (null != intent) { context.startActivity(intent); }
    }


}
