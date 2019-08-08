package com.jetpack.workermanager;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.io.IOException;

public class DownloadWorker extends Worker {
    public DownloadWorker(Context context, WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        for (int i = 0; i < 100; ++i) {
            if (isStopped()) {
                break;
            }

            try {
                downloadSynchronously("https://www.google.com");
            } catch (IOException e) {
                return Result.failure();
            }
        }

        return Result.success();
    }

    private void downloadSynchronously(String url) throws IOException {

    }
}
