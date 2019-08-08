package com.jetpack.workermanager;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class MyWorker extends Worker {
    public static final String TAG = "MyWorker";

    public static final String ARGS_A = "ARGS_A";
    public static final String ARGS_B = "ARGS_B";
    public static final String ARGS_RESULT = "ARGS_RESULT";

    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Data data = getInputData();
        int A = data.getInt(ARGS_A, 0);
        int B = data.getInt(ARGS_B, 1);
        int[] ret = data.getIntArray(ARGS_RESULT);

        try {
            Thread.sleep(4000); // 休息4s
        } catch(Exception ex) {
        	ex.printStackTrace();
        }

        Data result = new Data.Builder().putInt(ARGS_RESULT, (A+B)).build();
        return Result.success(result);
    }

    /*
    * 1. 取消任务
    * 2. 用已有的替换策略去提交，旧有的worker将会立即终止
    * 3. 约束条件不再满足
    * 4. 超过10分钟的执行权限
    *
    * */
    @Override
    public void onStopped() {
    }

}
