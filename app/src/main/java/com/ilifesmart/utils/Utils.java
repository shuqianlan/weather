package com.ilifesmart.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import androidx.appcompat.app.AlertDialog;

import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.ilifesmart.weather.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.UUID;

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

    public static Intent newIntent(Context context, Class<? extends Activity> cls) {
        return new Intent(context, cls);
    }

    public static void startActivity(Context context, Class<? extends Activity> cls) {
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
}
