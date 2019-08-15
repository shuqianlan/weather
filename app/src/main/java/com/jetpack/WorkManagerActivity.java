package com.jetpack;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.*;
import com.ilifesmart.ToolsApplication;
import com.ilifesmart.weather.R;
import com.jetpack.workermanager.MyWorker;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class WorkManagerActivity extends AppCompatActivity {

    public static final String TAG = "WorkManagerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_manager);

        // 定义何时及什么方式运行.

        // 约束条件
        Constraints constraints = new Constraints.Builder()
                .setRequiresCharging(true) // 充电
//                .setRequiresDeviceIdle(true) // 设备空闲
                .build();

        // 传参数
//        Data args = new Data.Builder()
//                .putInt(MyWorker.ARGS_A, 10)
//                .putInt(MyWorker.ARGS_B, 16)
//                .build();

        // 传递一组任务，并指定运行顺序

        // 一次性
        /*
        * ArrayCreatingInputMerger.class: 返回是数组 [32, 31]
        * OverwritingInputMerger.class: 返回所有key的值 31
        * 此处可以设置条件组合，在then中的Worker执行最终请求.
        * */
        OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(MyWorker.class)
                .setConstraints(constraints) // 设置约束条件
//                .setInputData(args) // 传参数
                .addTag("SUM")
                .setInputMerger(ArrayCreatingInputMerger.class)
                .setInitialDelay(10, TimeUnit.SECONDS)
                .setBackoffCriteria(
                        BackoffPolicy.LINEAR, // LINEAR:线性 EXPONENTIAL:指数
                        OneTimeWorkRequest.MIN_BACKOFF_MILLIS, // retry前的延时
                        TimeUnit.MILLISECONDS
                )
                .build();

        // 此处需对进度条的旋转做出UI显示.
        WorkManager.getInstance(ToolsApplication.getContext()).getWorkInfoByIdLiveData(workRequest.getId()).observe(this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(WorkInfo workInfo) {

                if (workInfo != null) {
                    Log.d(TAG, "onChanged: workInfo.state " + workInfo.getState().name());
                    if (workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                        Log.d(TAG, "onChanged: Finished");
                    }
                }
            }
        });
//        WorkManager.getInstance(ToolsApplication.getContext()).enqueue(periodicWorkRequest);

        Data data1 = new Data.Builder()
                .putInt(MyWorker.ARGS_A, 30)
                .build();
        OneTimeWorkRequest filter1 = new OneTimeWorkRequest.Builder(MyWorker.class)
                .setInputData(data1)
                .build();

        Data data2 = new Data.Builder()
                .putInt(MyWorker.ARGS_A, 31)
                .build();
        OneTimeWorkRequest filter2 = new OneTimeWorkRequest.Builder(MyWorker.class)
                .setInputData(data2)
                .build();

        WorkManager.getInstance(ToolsApplication.getContext())
                .beginWith(Arrays.asList(filter1, filter2)) // 并行
                .then(workRequest) // 上述执行完后执行
                .enqueue();

        // 取消任务
        // 如果已经结束，则啥也不做。否则，状态设定为CANCELLED。若果当前状态为RUNNING，则会触发onStopped()
        WorkManager.getInstance(ToolsApplication.getContext())
                .cancelWorkById(workRequest.getId());


        // 周期性任务
        // 不能使用chain
        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(MyWorker.class, 3000, TimeUnit.SECONDS)
                .build();

        WorkManager.getInstance(ToolsApplication.getContext())
//                .beginUniqueWork() // 执行beginWith的Chain操作!
                .enqueueUniquePeriodicWork("GaiLun", ExistingPeriodicWorkPolicy.REPLACE, periodicWorkRequest);

        // 大部分情况下，WorkManager已满足使用.
        // 若自定义可参考:https://developer.android.google.cn/topic/libraries/architecture/workmanager/advanced/custom-configuration

        int threads = Runtime.getRuntime().availableProcessors();
        ExecutorService executorService = Executors.newFixedThreadPool(threads);
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                	Thread.sleep(4000);
                    Log.d(TAG, "run: =================");
                } catch(Exception ex) {
                	ex.printStackTrace();
                }
            }
        });
    }
}
