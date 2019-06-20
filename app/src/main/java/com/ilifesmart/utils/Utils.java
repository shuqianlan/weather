package com.ilifesmart.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.ilifesmart.weather.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

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
}
